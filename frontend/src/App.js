import React, { useState } from "react";
import axios from "axios";

// Function to handle OAuth authentication and token retrieval
const authenticateWithGoogle = async () => {
  const clientId = process.env.REACT_APP_GOOGLE_OAUTH_CLIENT_ID;
  const redirectUri = "http://localhost:3000/oauth2/callback";
  const scope = "openid profile email";
  
  const authUrl = `https://accounts.google.com/o/oauth2/auth?response_type=code&client_id=${clientId}&redirect_uri=${redirectUri}&scope=${scope}&access_type=offline`;

  // Redirect to Google OAuth login
  window.location.href = authUrl;
};

// Function to fetch access token from the backend
const getAccessToken = async (code) => {
  const tokenUrl = "https://oauth2.googleapis.com/token";
  const redirectUri = "http://localhost:3000/oauth2/callback";  // Your redirect URI

  const formData = new URLSearchParams();
  formData.append("code", code);  // Authorization code
  formData.append("client_id", process.env.REACT_APP_GOOGLE_OAUTH_CLIENT_ID);  // Your client ID
  formData.append("client_secret", process.env.REACT_APP_GOOGLE_OAUTH_CLIENT_SECRET);  // Your client secret
  formData.append("redirect_uri", redirectUri);  // Your redirect URI
  formData.append("grant_type", "authorization_code");  // Grant type

  try {
    const response = await axios.post(tokenUrl, formData);
    return response.data.access_token;  // Return the access token
  } catch (error) {
    console.error("Error during token exchange:", error);
    return null;
  }
};

// Main App function
function App() {
  const [username, setUsername] = useState("");
  const [recommendations, setRecommendations] = useState(null);
  const [error, setError] = useState(null);
  const [accessToken, setAccessToken] = useState(null);
  const [mood, setMood] = useState("");  // Add mood state

  const handleGetRecommendations = async () => {
    if (!accessToken) {
      setError("You must be logged in first.");
      return;
    }

    if (!mood) {
      setError("Please enter a mood.");
      return;
    }

    try {
      setError(null);  // Clear any previous errors

      // Fetch all users from the API
      const usersResponse = await axios.get("http://localhost:8085/api/user", {
        headers: {
          Authorization: `Bearer ${accessToken}`,  // Use the access token for authenticated requests
        },
      });

      const users = usersResponse.data;

      // Find the user ID by matching the username
      const user = users.find((u) => u.username === username);
      if (!user) {
        setError("User not found.");
        setRecommendations(null);
        return;
      }

      const userId = user.id;

      // Fetch recommendations using the found user ID
      const recommendationsResponse = await axios.get(
        `http://localhost:8085/api/recommendations/${userId}`,
        {
          params: { mood },  // Send mood parameter
          headers: {
            Authorization: `Bearer ${accessToken}`,  // Use the access token for the recommendations API
          },
        }
      );

      setRecommendations(recommendationsResponse.data);  // Set recommendations from API response
    } catch (err) {
      setError("Could not fetch recommendations. Please try again.");
      setRecommendations(null);
    }
  };

  const handleLogin = async (code) => {
    // Use the authorization code to get an access token
    const token = await getAccessToken(code);
    if (token) {
      setAccessToken(token);  // Store the access token in the state
    } else {
      setError("Failed to authenticate. Please try again.");
    }
  };

  // Check for the OAuth code in the URL
  const urlParams = new URLSearchParams(window.location.search);
  const authCode = urlParams.get("code");

  if (authCode && !accessToken) {
    // Handle the OAuth callback
    handleLogin(authCode);
  }

  return (
    <div className="flex flex-col md:flex-row h-screen bg-gray-100 p-6">
      <div className="md:w-1/2 flex flex-col justify-center p-8 bg-white shadow-lg rounded-lg">
        <h2 className="text-3xl font-bold text-gray-800 mb-6">Get Activity Recommendations</h2>
        {!accessToken ? (
          <button
            onClick={authenticateWithGoogle}
            className="w-full bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 rounded-lg mt-4"
          >
            Log in with Google
          </button>
        ) : (
          <>
            <div className="mb-4">
              <label className="block text-gray-700 text-sm font-bold mb-2">Username</label>
              <input
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
                placeholder="Enter your username"
              />
            </div>
            <div className="mb-4">
              <label className="block text-gray-700 text-sm font-bold mb-2">Mood</label>
              <input
                type="text"
                value={mood}
                onChange={(e) => setMood(e.target.value)}
                className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
                placeholder="Enter your mood"
              />
            </div>
            <button
              onClick={handleGetRecommendations}
              className="w-full bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 rounded-lg mt-4"
            >
              Get Recommendations
            </button>
          </>
        )}
      </div>

      {/* Recommendations Section */}
      <div className="md:w-1/2 flex flex-col justify-center p-8">
        <h2 className="text-3xl font-bold text-gray-800 mb-6">Recommendations</h2>
        <div className="bg-white p-6 rounded-lg shadow-lg h-64 overflow-y-auto">
          {recommendations ? (
            <pre className="text-gray-700">
              {JSON.stringify(recommendations, null, 2)}
            </pre>
          ) : error ? (
            <p className="text-red-500">{error}</p>
          ) : (
            <p className="text-gray-500">Your recommendations will appear here.</p>
          )}
        </div>
      </div>
    </div>
  );
}

export default App;
