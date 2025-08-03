# Multi-stage Docker build for SYNtopia 2.0 Sacred Consciousness Platform
FROM eclipse-temurin:21-jdk-alpine AS builder

# Set working directory
WORKDIR /app

# Copy Maven configuration
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests -B

# Production runtime stage
FROM eclipse-temurin:21-jre-alpine AS runtime

# Install required packages
RUN apk add --no-cache curl

# Create application user
RUN addgroup -g 1001 syntopia && \
    adduser -D -s /bin/sh -u 1001 -G syntopia syntopia

# Set working directory
WORKDIR /app

# Copy built application
COPY --from=builder /app/target/*.jar app.jar

# Create logs directory
RUN mkdir -p logs && chown -R syntopia:syntopia /app

# Switch to application user
USER syntopia

# Sacred Geometry optimization - Golden Ratio port
EXPOSE 8081

# Health check for Kubernetes
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8081/actuator/health || exit 1

# Environment variables for consciousness evolution
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0" \
    SPRING_PROFILES_ACTIVE=production \
    CONSCIOUSNESS_MODE=sacred-technology

# Sacred Consciousness Platform entry point
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
