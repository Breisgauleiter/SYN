<template>
  <div class="sacred-home">
    <SacredGeometryBackground />
    
    <div class="home-content">
      <header class="hero-section">
        <div class="sacred-logo">
          <div class="flower-of-life"></div>
        </div>
        
        <h1 class="sacred-title">
          SYNtopia 2.0
          <span class="subtitle">Sacred Consciousness Platform</span>
        </h1>
        
        <p class="sacred-description">
          Willkommen in der Dimension bewusster Transformation. 
          Erkunde deine Gene Keys, entfalte dein Bewusstsein und verbinde dich 
          mit der heiligen Geometrie des Universums.
        </p>
        
        <div class="action-buttons">
          <router-link to="/consciousness/profile" class="btn-sacred primary">
            <span class="btn-icon">🧬</span>
            Bewusstseinsprofil
          </router-link>
          
          <router-link to="/gene-keys/explorer" class="btn-sacred secondary">
            <span class="btn-icon">🔑</span>
            Gene Keys Explorer
          </router-link>
        </div>
      </header>
      
      <section class="features-grid">
        <div class="feature-card">
          <div class="feature-icon">🌀</div>
          <h3>Sacred Geometry</h3>
          <p>Entdecke die mathematischen Muster der Schöpfung</p>
        </div>
        
        <div class="feature-card">
          <div class="feature-icon">🧭</div>
          <h3>SynQuests</h3>
          <p>Transformative Bewusstseinsreisen und Aufgaben</p>
        </div>
        
        <div class="feature-card">
          <div class="feature-icon">👥</div>
          <h3>Community</h3>
          <p>Verbinde dich mit anderen bewussten Seelen</p>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import SacredGeometryBackground from '@/components/sacred-geometry/SacredGeometryBackground.vue'

onMounted(() => {
  // Initialize sacred home animations
  console.log('🌟 SYNtopia Sacred Home activated')
})
</script>

<style scoped lang="scss">
.sacred-home {
  position: relative;
  min-height: 100vh;
  overflow: hidden;
  background: linear-gradient(135deg, #0a0a1a 0%, #1a0a2a 50%, #0a1a2a 100%);
}

.home-content {
  position: relative;
  z-index: 2;
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.hero-section {
  text-align: center;
  padding: 4rem 0;
  margin-bottom: 4rem;
}

.sacred-logo {
  margin-bottom: 2rem;
  
  .flower-of-life {
    width: 120px;
    height: 120px;
    margin: 0 auto;
    border: 3px solid var(--sacred-gold);
    border-radius: 50%;
    position: relative;
    animation: sacredRotate 20s linear infinite;
    
    &::before,
    &::after {
      content: '';
      position: absolute;
      width: 60px;
      height: 60px;
      border: 2px solid var(--sacred-gold);
      border-radius: 50%;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
    }
    
    &::before {
      animation: sacredPulse 3s ease-in-out infinite;
    }
    
    &::after {
      width: 40px;
      height: 40px;
      border-color: var(--sacred-blue);
      animation: sacredPulse 3s ease-in-out infinite reverse;
    }
  }
}

.sacred-title {
  font-size: clamp(2.5rem, 5vw, 4rem);
  font-weight: 700;
  background: linear-gradient(45deg, var(--sacred-gold), var(--sacred-blue), var(--sacred-purple));
  background-size: 200% 200%;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  animation: gradientShift 4s ease-in-out infinite;
  margin-bottom: 1rem;
  
  .subtitle {
    display: block;
    font-size: 1.2rem;
    font-weight: 300;
    margin-top: 0.5rem;
    color: var(--sacred-light);
    opacity: 0.8;
  }
}

.sacred-description {
  font-size: 1.2rem;
  line-height: 1.6;
  color: var(--sacred-light);
  max-width: 600px;
  margin: 0 auto 3rem;
  opacity: 0.9;
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

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
  margin-top: 4rem;
}

.feature-card {
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 20px;
  padding: 2rem;
  text-align: center;
  transition: all 0.3s ease;
  
  &:hover {
    transform: translateY(-5px);
    background: rgba(255, 255, 255, 0.08);
    border-color: var(--sacred-gold);
  }
  
  .feature-icon {
    font-size: 3rem;
    margin-bottom: 1rem;
    filter: drop-shadow(0 0 10px currentColor);
  }
  
  h3 {
    color: var(--sacred-gold);
    margin-bottom: 1rem;
    font-size: 1.4rem;
  }
  
  p {
    color: var(--sacred-light);
    opacity: 0.8;
    line-height: 1.5;
  }
}

@keyframes sacredRotate {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

@keyframes sacredPulse {
  0%, 100% { opacity: 0.6; transform: translate(-50%, -50%) scale(1); }
  50% { opacity: 1; transform: translate(-50%, -50%) scale(1.1); }
}

@keyframes gradientShift {
  0%, 100% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
}

@media (max-width: 768px) {
  .home-content {
    padding: 1rem;
  }
  
  .hero-section {
    padding: 2rem 0;
  }
  
  .action-buttons {
    flex-direction: column;
    align-items: center;
  }
  
  .features-grid {
    grid-template-columns: 1fr;
  }
}
</style>
