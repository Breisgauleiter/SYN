<template>
  <div class="not-found">
    <div class="sacred-404">
      <div class="sacred-symbol">
        <div class="infinity-loop"></div>
      </div>
      
      <h1 class="error-code">404</h1>
      <h2 class="error-title">Dimension nicht gefunden</h2>
      <p class="error-message">
        Diese Bewusstseinsebene existiert noch nicht in unserem Sacred Space.
        Kehre zurück zur Hauptdimension und erkunde andere Pfade der Transformation.
      </p>
      
      <div class="action-buttons">
        <router-link to="/" class="btn-sacred primary">
          <span class="btn-icon">🏠</span>
          Zurück zur Hauptdimension
        </router-link>
        
        <router-link to="/consciousness/profile" class="btn-sacred secondary">
          <span class="btn-icon">🧬</span>
          Bewusstseinsprofil
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'

onMounted(() => {
  console.log('🌌 404 - Exploring uncharted consciousness dimensions')
})
</script>

<style scoped lang="scss">
.not-found {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #0a0a1a 0%, #1a0a2a 50%, #0a1a2a 100%);
  padding: 2rem;
}

.sacred-404 {
  text-align: center;
  max-width: 600px;
  color: var(--sacred-light);
}

.sacred-symbol {
  margin-bottom: 2rem;
  
  .infinity-loop {
    width: 120px;
    height: 60px;
    margin: 0 auto;
    position: relative;
    
    &::before,
    &::after {
      content: '';
      position: absolute;
      width: 60px;
      height: 60px;
      border: 3px solid var(--sacred-gold);
      border-radius: 50%;
      animation: infinityRotate 4s linear infinite;
    }
    
    &::before {
      left: 0;
    }
    
    &::after {
      right: 0;
      animation-direction: reverse;
      border-color: var(--sacred-blue);
    }
  }
}

.error-code {
  font-size: clamp(4rem, 8vw, 8rem);
  font-weight: 700;
  background: linear-gradient(45deg, var(--sacred-gold), var(--sacred-blue));
  background-size: 200% 200%;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  animation: gradientShift 3s ease-in-out infinite;
  margin-bottom: 1rem;
  line-height: 1;
}

.error-title {
  font-size: 2rem;
  color: var(--sacred-gold);
  margin-bottom: 1.5rem;
  font-weight: 600;
}

.error-message {
  font-size: 1.1rem;
  line-height: 1.6;
  opacity: 0.9;
  margin-bottom: 3rem;
}

.action-buttons {
  display: flex;
  gap: 1.5rem;
  justify-content: center;
  flex-wrap: wrap;
}

.btn-sacred {
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
  padding: 1rem 2rem;
  border-radius: 50px;
  text-decoration: none;
  font-weight: 600;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
  
  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: -100%;
    width: 100%;
    height: 100%;
    background: linear-gradient(90deg, transparent, rgba(255,255,255,0.2), transparent);
    transition: left 0.5s ease;
  }
  
  &:hover::before {
    left: 100%;
  }
  
  &.primary {
    background: linear-gradient(45deg, var(--sacred-gold), var(--sacred-orange));
    color: var(--sacred-dark);
    border: 2px solid transparent;
    
    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 10px 25px rgba(255, 215, 0, 0.3);
    }
  }
  
  &.secondary {
    background: transparent;
    color: var(--sacred-light);
    border: 2px solid var(--sacred-blue);
    
    &:hover {
      background: var(--sacred-blue);
      transform: translateY(-2px);
      box-shadow: 0 10px 25px rgba(102, 102, 255, 0.3);
    }
  }
}

@keyframes infinityRotate {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

@keyframes gradientShift {
  0%, 100% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
}

@media (max-width: 768px) {
  .action-buttons {
    flex-direction: column;
    align-items: center;
  }
  
  .btn-sacred {
    width: 100%;
    max-width: 300px;
    justify-content: center;
  }
}
</style>
