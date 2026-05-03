#!/bin/bash
if [ -z "$1" ]; then
  echo "Usage: ./start.sh <PORT>"
  exit 1
fi
export APP_PORT=$1

docker-compose up --build -d
echo "Stock Market Simulator started and highly available at http://localhost:$APP_PORT"