#!/bin/bash

# Assuming your Spring app is running on port 8080
SPRING_PID=$(lsof -t -i:8080)

if [ -n "$SPRING_PID" ]; then
  echo "Stopping the Spring application..."
  kill "$SPRING_PID"
fi
