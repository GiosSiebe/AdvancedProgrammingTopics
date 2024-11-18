import React, { useState } from "react";
import axios from "axios";

// Main App function
function App() {
  const [userId, setUserId] = useState(""); // Directly use user ID
  const [recommendations, setRecommendations] = useState(null);
  const [error, setError] = useState(null);

  const handleGetRecommendations = async () => {
    if (!userId) {
      setError("You must enter a user ID.");
      return;
    }

    try {
      setError(null);  // Clear any previous errors

      // Fetch recommendations using the user ID
      const recommendationsResponse = await axios.get(
        `http://localhost:8085/api/recommendations/${userId}`
      );

      setRecommendations(recommendationsResponse.data);  // Set recommendations from API response
    } catch (err) {
      setError("Could not fetch recommendations. Please try again.");
      setRecommendations(null);
    }
  };

  return (
    <div className="flex flex-col md:flex-row h-screen bg-gray-100 p-6">
      <div className="md:w-1/2 flex flex-col justify-center p-8 bg-white shadow-lg rounded-lg">
        <h2 className="text-3xl font-bold text-gray-800 mb-6">Get Activity Recommendations</h2>
        <div className="mb-4">
          <label className="block text-gray-700 text-sm font-bold mb-2">User ID</label>
          <input
            type="text"
            value={userId}
            onChange={(e) => setUserId(e.target.value)}
            className="w-full px-3 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
            placeholder="Enter your user ID"
          />
        </div>
        <button
          onClick={handleGetRecommendations}
          className="w-full bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 rounded-lg mt-4"
        >
          Get Recommendations
        </button>
      </div>

      {/* Recommendations Section */}
      <div className="md:w-1/2 flex flex-col justify-center p-8">
        <h2 className="text-3xl font-bold text-gray-800 mb-6">Recommendations</h2>
        <div className="bg-white p-6 rounded-lg shadow-lg h-64 overflow-y-auto">
          {recommendations ? (
            <ul className="space-y-4">
              {recommendations.map((rec, index) => (
                <li key={index} className="bg-gray-50 p-4 rounded-lg shadow-md">
                  <h3 className="text-lg font-bold text-gray-700">{rec.activity.activity}</h3>
                  <p className="text-sm text-gray-600">{rec.activity.description}</p>
                  <p className="text-sm text-gray-500">
                    <strong>Category:</strong> {rec.activity.category}
                  </p>
                  <p className="text-sm text-gray-500">
                    <strong>Duration:</strong> {rec.activity.duration} minutes
                  </p>
                  <p className="text-sm text-gray-500">
                    <strong>Energy Level:</strong> {rec.activity.energyLevelRequired}
                  </p>
                </li>
              ))}
            </ul>
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
