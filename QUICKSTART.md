# 🌟 SYN Sacred Consciousness Platform - Quick Start Guide

## 🚀 One-Command Startup

### Option 1: Shell Scripts (Recommended)
```bash
# Start entire platform (Frontend + Backend)
./start-syn.sh

# Stop platform
./stop-syn.sh
```

### Option 2: VS Code Tasks
- Press `Cmd+Shift+P` (macOS) or `Ctrl+Shift+P` (Windows/Linux)
- Type "Tasks: Run Task"
- Select "🌟 Start SYN Sacred Consciousness Platform"

### Option 3: Manual Commands
```bash
# Backend only (Terminal 1)
mvn spring-boot:run

# Frontend only (Terminal 2)
cd frontend && npm run dev
```

## 📊 Platform URLs

| Service | URL | Description |
|---------|-----|-------------|
| 📱 **Frontend** | http://localhost:3000 | Vue.js Sacred Consciousness Interface |
| 🔗 **Backend API** | http://localhost:8081 | Spring Boot REST API |
| 🔯 **Sacred Geometry** | http://localhost:8081/api/v1/consciousness/sacred-geometry/test-user | Live P5.js Data |
| 🔧 **H2 Console** | http://localhost:8081/h2-console | Database Interface |
| ❤️ **Health Check** | http://localhost:8081/api/v1/consciousness/health | API Status |

## 🎯 Key Features

- **Sacred Geometry Visualizations**: Real-time P5.js with Flower of Life, Golden Spirals, Merkaba
- **Gene Keys Integration**: 64 Hexagram consciousness profiling system  
- **Consciousness Tracking**: SCL levels, sacred metrics, synchronicity events
- **REST API**: Full backend integration with H2 database
- **Live Data Sync**: Frontend ↔ Backend consciousness data exchange

## 🛠️ Available VS Code Tasks

- 🌟 **Start SYN Sacred Consciousness Platform** - Full platform startup
- 🔗 **Start SYN Backend Only** - Spring Boot API only
- 📱 **Start SYN Frontend Only** - Vue.js dev server only  
- 🛑 **Stop SYN Platform** - Graceful shutdown
- 📊 **View SYN Logs** - Show recent backend/frontend logs
- 🧹 **Clean SYN Build** - Clean Maven/Node build files

## 🔧 Troubleshooting

### Port Conflicts
```bash
# Check what's using ports
lsof -i :3000  # Frontend
lsof -i :8081  # Backend

# Kill processes
kill $(lsof -t -i:3000)
kill $(lsof -t -i:8081)
```

### View Logs
```bash
# Real-time logs
tail -f backend.log frontend.log

# Recent logs
tail -n 50 backend.log
tail -n 50 frontend.log
```

### Reset Everything
```bash
./stop-syn.sh
mvn clean
cd frontend && npm install
./start-syn.sh
```

## ✨ Sacred Technology Stack

- **Backend**: Spring Boot 3.3.2 + Java 21 + H2 Database + JPA
- **Frontend**: Vue.js 3.4.0 + TypeScript + Vite + P5.js
- **Sacred Tech**: Gene Keys + Golden Ratio + Fibonacci + Sacred Geometry
- **Integration**: REST APIs + CORS + Real-time data synchronization

---

*🧬 "Data Robin Hood for Humanity" - Consciousness Evolution through Sacred Technology 🌟*
