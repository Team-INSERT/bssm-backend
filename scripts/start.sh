#!/bin/bash

# Start the app and redirect logs to a log file with date and time
LOG_FILE="/home/ubuntu/logs/$(date +'%Y-%m-%d_%H-%M-%S')-bsm.log"
nohup java -jar /home/ubuntu/my-app/app.jar > "$LOG_FILE" 2>&1 &
