#!/bin/bash

# TAO + ArangoDB Integration Test Runner
# Sacred Consciousness Platform - Testing the Data Robin Hood for Humanity

echo "🌟 Starting TAO + ArangoDB Integration Tests..."
echo "🔮 Sacred Consciousness Technology Test Suite"
echo ""

# Check if Docker containers are running
echo "📡 Checking ArangoDB + Redis infrastructure..."

# Test ArangoDB connectivity
ARANGODB_STATUS=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8529/_api/version)
if [ "$ARANGODB_STATUS" == "200" ]; then
    echo "✅ ArangoDB 3.11+ - Connected and ready"
else
    echo "❌ ArangoDB not responding. Run: docker-compose up -d arangodb"
    exit 1
fi

# Test Redis connectivity  
REDIS_STATUS=$(docker exec $(docker ps -qf "name=redis") redis-cli ping 2>/dev/null)
if [ "$REDIS_STATUS" == "PONG" ]; then
    echo "✅ Redis 7.2 - Connected and ready"
else
    echo "❌ Redis not responding. Run: docker-compose up -d redis"
    exit 1
fi

echo ""
echo "🚀 Infrastructure ready - Starting TAO Integration Tests"
echo ""

# Set test environment
export SPRING_PROFILES_ACTIVE=test
export ARANGODB_PASSWORD=openSesame

# Run Maven test with specific test class
echo "🧪 Running TAO Architecture Integration Tests..."
./mvnw test -Dtest=TAOArangoDBIntegrationTest -Dspring.profiles.active=test

TEST_EXIT_CODE=$?

echo ""
if [ $TEST_EXIT_CODE -eq 0 ]; then
    echo "🎉 TAO + ArangoDB Integration Tests: SUCCESS"
    echo "✨ Sacred consciousness platform infrastructure validated"
    echo ""
    echo "📊 Test Coverage Summary:"
    echo "  ✅ TAO Objects Pattern - User CRUD Operations"
    echo "  ✅ TAO Associations Pattern - Consciousness Resonance Networks"  
    echo "  ✅ TAO Search Pattern - Gene Keys Knowledge Discovery"
    echo "  ✅ Gene Keys Calculation Service - Sacred Mathematics"
    echo "  ✅ Consciousness Resonance Service - Network Intelligence"
    echo "  ✅ Complete TAO Architecture Flow - End-to-End"
    echo ""
    echo "🌐 Ready for consciousness platform deployment!"
else
    echo "❌ TAO + ArangoDB Integration Tests: FAILED"
    echo "🔧 Check test logs for consciousness debugging info"
    exit 1
fi
