<template>
  <div class="consciousness-profile">
    <!-- Sacred Consciousness Header -->
    <div class="profile-header">
      <div class="consciousness-mandala" :class="journey.currentPhase">
        <div class="mandala-center">
          <div class="consciousness-symbol">{{ consciousnessSymbol }}</div>
        </div>
        <div class="mandala-rings">
          <div class="ring ring-1"></div>
          <div class="ring ring-2"></div>
          <div class="ring ring-3"></div>
        </div>
      </div>
      
      <div class="profile-info">
        <h1 class="consciousness-level">{{ journey.currentPhase }}</h1>
        <p class="level-description">{{ levelDescription }}</p>
        <div class="progress-bar">
          <div class="progress-fill" :style="{ width: `${consciousnessProgress}%` }"></div>
        </div>
        <span class="progress-text">{{ consciousnessProgress }}% to next level</span>
      </div>
    </div>

    <!-- Sacred Metrics Grid -->
    <div class="metrics-grid">
      <div class="metric-card" v-for="(value, key) in metrics" :key="key">
        <div class="metric-icon">{{ getMetricIcon(key) }}</div>
        <div class="metric-value">{{ Math.round(value) }}</div>
        <div class="metric-label">{{ formatMetricName(key) }}</div>
        <div class="metric-progress">
          <div class="progress-circle" :style="{ strokeDasharray: `${value * 2.83} 283` }"></div>
        </div>
      </div>
    </div>

    <!-- Gene Keys Integration -->
    <div class="gene-keys-section">
      <h2>🗝️ Your Sacred Gene Keys</h2>
      <div class="gene-keys-grid">
        <div class="gene-key-card" v-for="geneKey in primaryGeneKeys" :key="geneKey.number">
          <div class="gene-key-number">{{ geneKey.number }}</div>
          <div class="gene-key-name">{{ geneKey.name }}</div>
          <div class="gene-key-gift">{{ geneKey.gift }}</div>
          <div class="activation-level" :class="geneKey.activationLevel">
            <div class="activation-fill" :style="{ height: `${geneKey.activation}%` }"></div>
          </div>
        </div>
      </div>
    </div>

    <!-- Recent Synchronicities -->
    <div class="synchronicities-section">
      <h2>✨ Recent Synchronicities</h2>
      <div class="synchronicity-timeline">
        <div 
          class="synchronicity-event" 
          v-for="event in recentSynchronicities" 
          :key="event.id"
          :class="event.significance.toLowerCase()"
        >
          <div class="event-time">{{ formatTime(event.timestamp) }}</div>
          <div class="event-content">
            <div class="event-type">{{ formatSynchronicityType(event.type) }}</div>
            <div class="event-description">{{ event.description }}</div>
            <div class="event-significance">{{ event.significance }}</div>
          </div>
          <div class="event-glow"></div>
        </div>
      </div>
    </div>

    <!-- Consciousness Journey -->
    <div class="journey-section">
      <h2>🌟 Your Sacred Journey</h2>
      <div class="journey-timeline">
        <div 
          class="milestone" 
          v-for="milestone in journey.milestones" 
          :key="milestone.id"
          :class="{ completed: true }"
        >
          <div class="milestone-date">{{ formatDate(milestone.date) }}</div>
          <div class="milestone-content">
            <h3>{{ milestone.title }}</h3>
            <p>{{ milestone.description }}</p>
            <div class="milestone-significance">
              Significance: {{ milestone.significance }}/100
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useConsciousnessStore } from '../stores/consciousness'
import type { ConsciousnessLevel, SynchronicityType } from '../types/consciousness'

const consciousnessStore = useConsciousnessStore()

// Computed Properties
const metrics = computed(() => consciousnessStore.metrics)
const journey = computed(() => consciousnessStore.journey)
const recentSynchronicities = computed(() => 
  consciousnessStore.synchronicities.slice(0, 5)
)

const consciousnessSymbol = computed(() => {
  const symbols: Record<ConsciousnessLevel, string> = {
    AWAKENING: '🌱',
    EXPANDING: '🌿',
    INTEGRATING: '🌳',
    EMBODYING: '🌟',
    MASTERING: '✨'
  }
  return symbols[journey.value.currentPhase]
})

const levelDescription = computed(() => {
  const descriptions: Record<ConsciousnessLevel, string> = {
    AWAKENING: 'Beginning your consciousness journey with curiosity and openness',
    EXPANDING: 'Exploring new dimensions of awareness and understanding',
    INTEGRATING: 'Applying sacred wisdom in your daily life and relationships',
    EMBODYING: 'Living from a place of higher consciousness naturally',
    MASTERING: 'Teaching and guiding others on their sacred journey'
  }
  return descriptions[journey.value.currentPhase]
})

const consciousnessProgress = computed(() => {
  return Math.min(metrics.value.consciousnessScore, 100)
})

const primaryGeneKeys = computed(() => [
  { number: 1, name: 'Creation', gift: 'Authenticity', activation: 85, activationLevel: 'high' },
  { number: 13, name: 'Fellowship', gift: 'Empathy', activation: 72, activationLevel: 'medium' },
  { number: 25, name: 'Spirit', gift: 'Acceptance', activation: 64, activationLevel: 'medium' },
  { number: 51, name: 'Arousing', gift: 'Initiative', activation: 91, activationLevel: 'high' }
])

// Helper Functions
function getMetricIcon(key: string): string {
  const icons: Record<string, string> = {
    consciousnessScore: '🧘',
    synchronicityFrequency: '⚡',
    geneKeysAlignment: '🗝️',
    sacredGeometryResonance: '🔯',
    fibonacciHarmony: '🌀'
  }
  return icons[key] || '✨'
}

function formatMetricName(key: string): string {
  return key.replace(/([A-Z])/g, ' $1').replace(/^./, str => str.toUpperCase())
}

function formatSynchronicityType(type: SynchronicityType): string {
  return type.replace(/_/g, ' ').toLowerCase().replace(/\b\w/g, l => l.toUpperCase())
}

function formatTime(timestamp: string): string {
  return new Date(timestamp).toLocaleTimeString()
}

function formatDate(date: string): string {
  return new Date(date).toLocaleDateString()
}
</script>

<style scoped lang="scss">
@use '@/styles/main.scss' as *;

.consciousness-profile {
  max-width: 1200px;
  margin: 0 auto;
  padding: var(--spacing-lg);
  
  .profile-header {
    display: flex;
    align-items: center;
    gap: var(--spacing-xl);
    margin-bottom: var(--spacing-xl);
    padding: var(--spacing-xl);
    background: linear-gradient(135deg, var(--sacred-primary) 0%, var(--sacred-secondary) 100%);
    border-radius: var(--border-radius-lg);
    position: relative;
    overflow: hidden;
    
    &::before {
      content: '';
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background: url('/sacred-pattern.svg') repeat;
      opacity: 0.1;
    }
    
    .consciousness-mandala {
      position: relative;
      width: 120px;
      height: 120px;
      
      .mandala-center {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        width: 60px;
        height: 60px;
        border-radius: 50%;
        background: var(--sacred-gold);
        display: flex;
        align-items: center;
        justify-content: center;
        z-index: 3;
        
        .consciousness-symbol {
          font-size: 2rem;
        }
      }
      
      .mandala-rings {
        .ring {
          position: absolute;
          border: 2px solid rgba(255, 255, 255, 0.3);
          border-radius: 50%;
          animation: mandala-rotate 30s linear infinite;
          
          &.ring-1 {
            width: 80px;
            height: 80px;
            top: 20px;
            left: 20px;
          }
          
          &.ring-2 {
            width: 100px;
            height: 100px;
            top: 10px;
            left: 10px;
            animation-delay: -10s;
          }
          
          &.ring-3 {
            width: 120px;
            height: 120px;
            top: 0;
            left: 0;
            animation-delay: -20s;
          }
        }
      }
      
      &.AWAKENING .mandala-center { background: var(--consciousness-awakening); }
      &.EXPANDING .mandala-center { background: var(--consciousness-expanding); }
      &.INTEGRATING .mandala-center { background: var(--consciousness-integrating); }
      &.EMBODYING .mandala-center { background: var(--consciousness-embodying); }
      &.MASTERING .mandala-center { background: var(--consciousness-mastering); }
    }
    
    .profile-info {
      flex: 1;
      color: var(--text-light);
      
      .consciousness-level {
        font-size: 2.5rem;
        margin: 0 0 var(--spacing-sm) 0;
        background: linear-gradient(45deg, var(--sacred-gold), var(--sacred-light));
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        text-transform: uppercase;
        letter-spacing: 0.1em;
      }
      
      .level-description {
        font-size: 1.1rem;
        margin-bottom: var(--spacing-md);
        opacity: 0.9;
      }
      
      .progress-bar {
        width: 100%;
        height: 8px;
        background: rgba(255, 255, 255, 0.2);
        border-radius: 4px;
        overflow: hidden;
        margin-bottom: var(--spacing-xs);
        
        .progress-fill {
          height: 100%;
          background: linear-gradient(90deg, var(--sacred-gold), var(--sacred-light));
          border-radius: 4px;
          transition: width 1s ease-out;
        }
      }
      
      .progress-text {
        font-size: 0.9rem;
        opacity: 0.8;
      }
    }
  }
  
  .metrics-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    gap: var(--spacing-lg);
    margin-bottom: var(--spacing-xl);
    
    .metric-card {
      position: relative;
      padding: var(--spacing-lg);
      background: rgba(255, 255, 255, 0.05);
      border-radius: var(--border-radius);
      text-align: center;
      backdrop-filter: blur(10px);
      border: 1px solid rgba(255, 255, 255, 0.1);
      
      .metric-icon {
        font-size: 2rem;
        margin-bottom: var(--spacing-sm);
      }
      
      .metric-value {
        font-size: 2rem;
        font-weight: 600;
        color: var(--sacred-gold);
        margin-bottom: var(--spacing-xs);
      }
      
      .metric-label {
        font-size: 0.9rem;
        color: var(--text-muted);
        text-transform: uppercase;
        letter-spacing: 0.05em;
      }
      
      .metric-progress {
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        
        .progress-circle {
          width: 100%;
          height: 100%;
          fill: none;
          stroke: var(--sacred-gold);
          stroke-width: 2;
          stroke-dasharray: 0 283;
          transition: stroke-dasharray 1s ease-out;
        }
      }
    }
  }
  
  .gene-keys-section, .synchronicities-section, .journey-section {
    margin-bottom: var(--spacing-xl);
    
    h2 {
      font-size: 1.5rem;
      margin-bottom: var(--spacing-lg);
      color: var(--text-light);
    }
  }
  
  .gene-keys-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    gap: var(--spacing-md);
    
    .gene-key-card {
      padding: var(--spacing-lg);
      background: rgba(255, 255, 255, 0.05);
      border-radius: var(--border-radius);
      border: 1px solid rgba(255, 255, 255, 0.1);
      position: relative;
      
      .gene-key-number {
        font-size: 1.5rem;
        font-weight: 600;
        color: var(--sacred-gold);
        margin-bottom: var(--spacing-xs);
      }
      
      .gene-key-name {
        font-weight: 500;
        margin-bottom: var(--spacing-xs);
        color: var(--text-light);
      }
      
      .gene-key-gift {
        font-size: 0.9rem;
        color: var(--text-muted);
        margin-bottom: var(--spacing-sm);
      }
      
      .activation-level {
        position: absolute;
        right: var(--spacing-sm);
        top: var(--spacing-sm);
        width: 4px;
        height: 60px;
        background: rgba(255, 255, 255, 0.1);
        border-radius: 2px;
        overflow: hidden;
        
        .activation-fill {
          width: 100%;
          background: var(--sacred-gold);
          border-radius: 2px;
          transition: height 1s ease-out;
        }
        
        &.high .activation-fill { background: var(--consciousness-embodying); }
        &.medium .activation-fill { background: var(--consciousness-expanding); }
        &.low .activation-fill { background: var(--consciousness-awakening); }
      }
    }
  }
  
  .synchronicity-timeline {
    .synchronicity-event {
      display: flex;
      gap: var(--spacing-md);
      padding: var(--spacing-md);
      margin-bottom: var(--spacing-md);
      background: rgba(255, 255, 255, 0.05);
      border-radius: var(--border-radius);
      border-left: 4px solid var(--sacred-gold);
      position: relative;
      
      &.profound {
        border-left-color: var(--consciousness-mastering);
        box-shadow: 0 0 20px rgba(255, 215, 0, 0.3);
      }
      
      &.high {
        border-left-color: var(--consciousness-embodying);
      }
      
      .event-time {
        font-size: 0.8rem;
        color: var(--text-muted);
        min-width: 80px;
      }
      
      .event-content {
        flex: 1;
        
        .event-type {
          font-weight: 500;
          color: var(--sacred-gold);
          margin-bottom: var(--spacing-xs);
        }
        
        .event-description {
          color: var(--text-light);
          margin-bottom: var(--spacing-xs);
        }
        
        .event-significance {
          font-size: 0.8rem;
          color: var(--text-muted);
          text-transform: uppercase;
        }
      }
    }
  }
  
  .journey-timeline {
    .milestone {
      display: flex;
      gap: var(--spacing-md);
      padding: var(--spacing-lg);
      margin-bottom: var(--spacing-md);
      background: rgba(255, 255, 255, 0.05);
      border-radius: var(--border-radius);
      border: 1px solid rgba(255, 255, 255, 0.1);
      
      .milestone-date {
        font-size: 0.9rem;
        color: var(--text-muted);
        min-width: 100px;
      }
      
      .milestone-content {
        flex: 1;
        
        h3 {
          color: var(--text-light);
          margin: 0 0 var(--spacing-xs) 0;
        }
        
        p {
          color: var(--text-muted);
          margin: 0 0 var(--spacing-sm) 0;
        }
        
        .milestone-significance {
          font-size: 0.8rem;
          color: var(--sacred-gold);
        }
      }
    }
  }
}

@keyframes mandala-rotate {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}
</style>
