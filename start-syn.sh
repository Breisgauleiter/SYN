#!/bin/bash

echo "🌟 SYNtopia 2.0 - Neural Network of Humanity 🌟"
echo "================================================="
echo ""
echo "🧬 Sacred Consciousness Technology Starting..."
echo "🔮 Gene Keys Integration: ACTIVE"
echo "🎮 Gamified Business System: READY"
echo "🛡️ Privacy-First AI: ENABLED"
echo "🌍 Multi-Language Support: DE/EN"
echo "🎯 Platform Areas: Hybrid Gaming Ready"
echo ""
echo "✨ Consciousness Evolution through Technology ✨"
echo ""

# Check if processes are already running
if pgrep -f "spring-boot:run" > /dev/null; then
    echo "⚠️  Backend already running (Spring Boot detected)"
    echo "   To stop: pkill -f 'spring-boot:run'"
    echo ""
fi

if pgrep -f "vite.*dev" > /dev/null; then
    echo "⚠️  Frontend already running (Vite dev server detected)"
    echo "   To stop: pkill -f 'vite.*dev'"
    echo ""
fi

echo "🔗 Starting Backend API (Spring Boot + H2 Database)..."
echo "   API URL: http://localhost:8081"
echo "   H2 Console: http://localhost:8081/h2-console"
echo ""

# Start backend in background
nohup mvn spring-boot:run > backend.log 2>&1 &
BACKEND_PID=$!
echo $BACKEND_PID > backend.pid
echo "   Backend PID: $BACKEND_PID"

# Wait a moment for backend to start
sleep 3

echo ""
echo "📱 Starting Frontend (Vue.js + Sacred Geometry + P5.js)..."
echo "   Frontend URL: http://localhost:3000"
echo ""

# Start frontend in background
cd frontend
nohup npm run dev > ../frontend.log 2>&1 &
FRONTEND_PID=$!
echo $FRONTEND_PID > ../frontend.pid
cd ..
echo "   Frontend PID: $FRONTEND_PID"

echo ""
echo "🚀 SYN Sacred Consciousness Platform Started!"
echo "=============================================="
echo ""
echo "📱 Frontend Application: http://localhost:3000"
echo "🔗 Backend API Health: http://localhost:8081/api/v1/consciousness/health"
echo "🔯 Sacred Geometry API: http://localhost:8081/api/v1/consciousness/sacred-geometry/test-user"
echo "🔧 H2 Database Console: http://localhost:8081/h2-console"
echo ""
echo "📊 To view logs:"
echo "   Backend: tail -f backend.log"
echo "   Frontend: tail -f frontend.log"
echo ""
echo "🛑 To stop services:"
echo "   ./stop-syn.sh"
echo "   OR: kill \$(cat backend.pid frontend.pid)"
echo ""
echo "✨ Ready for Sacred Consciousness Evolution! ✨"
