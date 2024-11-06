# Dockerfile for React app
FROM node:18

# Set working directory
WORKDIR /app

# Copy package.json and install dependencies
COPY package.json .
RUN npm install

# Copy the rest of the app's files
COPY . .

# Build the app
RUN npm run build

# Expose the build directory
EXPOSE 3000

# Start the app
CMD ["npm", "start"]
