#!/bin/bash

echo "🛑 Stopping SYN Sacred Consciousness Platform..."
echo "================================================"
echo ""

# Stop backend if running
if [ -f backend.pid ]; then
    BACKEND_PID=$(cat backend.pid)
    echo "🔗 Stopping Backend (PID: $BACKEND_PID)..."
    kill $BACKEND_PID 2>/dev/null
    rm backend.pid
    echo "   ✅ Backend stopped"
else
    echo "🔗 No backend PID file found, checking for running processes..."
    pkill -f "spring-boot:run" 2>/dev/null && echo "   ✅ Backend processes stopped"
fi

# Stop frontend if running
if [ -f frontend.pid ]; then
    FRONTEND_PID=$(cat frontend.pid)
    echo "📱 Stopping Frontend (PID: $FRONTEND_PID)..."
    kill $FRONTEND_PID 2>/dev/null
    rm frontend.pid
    echo "   ✅ Frontend stopped"
else
    echo "📱 No frontend PID file found, checking for running processes..."
    pkill -f "vite.*dev" 2>/dev/null && echo "   ✅ Frontend processes stopped"
fi

echo ""
echo "🧹 Cleaning up..."
echo "   Log files preserved: backend.log, frontend.log"

echo ""
echo "✨ SYN Sacred Consciousness Platform stopped gracefully ✨"
echo ""
