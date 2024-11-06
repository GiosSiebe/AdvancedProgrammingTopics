# Dockerfile for React app
FROM node:18

# Set working directory
WORKDIR /app

# Copy package.json and install dependencies
COPY package.json .
RUN npm install

# Copy the rest of the app's files
COPY . .

# Accept build arguments and set them as environment variables
ARG REACT_APP_GOOGLE_OAUTH_CLIENT_ID
ARG REACT_APP_GOOGLE_OAUTH_CLIENT_SECRET

ENV REACT_APP_GOOGLE_OAUTH_CLIENT_ID=$REACT_APP_GOOGLE_OAUTH_CLIENT_ID
ENV REACT_APP_GOOGLE_OAUTH_CLIENT_SECRET=$REACT_APP_GOOGLE_OAUTH_CLIENT_SECRET

# Build the app
RUN npm run build

# Expose the build directory
EXPOSE 3000

# Start the app
CMD ["npm", "start"]
