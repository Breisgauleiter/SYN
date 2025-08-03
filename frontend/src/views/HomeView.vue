<template>
  <div class="sacred-home">
    <!-- Consciousness Profile Section -->
    <section class="consciousness-overview">
      <ConsciousnessProfile />
    </section>

    <!-- Sacred Quests Dashboard -->
    <section class="quests-dashboard">
      <div class="section-header">
        <h2>⚡ Sacred Quests</h2>
        <router-link to="/quests" class="view-all-btn">View All</router-link>
      </div>
      <div class="quests-grid">
        <div 
          class="quest-card" 
          v-for="quest in featuredQuests" 
          :key="quest.id"
          :class="quest.difficulty.toLowerCase()"
        >
          <div class="quest-header">
            <div class="quest-type">{{ quest.type }}</div>
            <div class="quest-difficulty">{{ quest.difficulty }}</div>
          </div>
          <h3 class="quest-title">{{ quest.title }}</h3>
          <p class="quest-description">{{ quest.description }}</p>
          <div class="quest-progress">
            <div class="progress-bar">
              <div class="progress-fill" :style="{ width: `${quest.progress}%` }"></div>
            </div>
            <span class="progress-text">{{ quest.progress }}% Complete</span>
          </div>
          <div class="quest-rewards">
            <span class="reward-item">+{{ quest.experiencePoints }} XP</span>
            <span class="reward-item">+{{ quest.consciousnessGrowth }} Consciousness</span>
          </div>
        </div>
      </div>
    </section>

    <!-- Recent Community Activity -->
    <section class="community-feed">
      <div class="section-header">
        <h2>🌟 Community Consciousness</h2>
        <router-link to="/community" class="view-all-btn">Join Community</router-link>
      </div>
      <div class="activity-stream">
        <div 
          class="activity-item" 
          v-for="activity in recentActivity" 
          :key="activity.id"
        >
          <div class="activity-avatar">{{ activity.user[0] }}</div>
          <div class="activity-content">
            <div class="activity-text">
              <strong>{{ activity.user }}</strong> {{ activity.action }}
            </div>
            <div class="activity-time">{{ formatTime(activity.timestamp) }}</div>
          </div>
          <div class="activity-type">{{ activity.type }}</div>
        </div>
      </div>
    </section>

    <!-- Sacred Insights -->
    <section class="sacred-insights">
      <div class="section-header">
        <h2>✨ Sacred Insights</h2>
      </div>
      <div class="insights-grid">
        <div class="insight-card" v-for="insight in todaysInsights" :key="insight.id">
          <div class="insight-category">{{ insight.category }}</div>
          <div class="insight-text">{{ insight.insight }}</div>
          <div class="insight-actions">
            <button class="action-btn reflect">Reflect</button>
            <button class="action-btn integrate">Integrate</button>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue'
import ConsciousnessProfile from '../components/ConsciousnessProfile.vue'
import { useConsciousnessStore } from '../stores/consciousness'
import type { Quest } from '../types/consciousness'

const consciousnessStore = useConsciousnessStore()

// Sample data for demonstration
const featuredQuests = computed((): Quest[] => [
  {
    id: '1',
    title: 'Sacred Meditation Practice',
    description: 'Establish a daily 20-minute meditation practice to deepen your consciousness connection.',
    type: 'PLATFORM',
    difficulty: 'BEGINNER',
    minimumLevel: 'AWAKENING',
    status: 'ACTIVE',
    progress: 65,
    experiencePoints: 150,
    consciousnessGrowth: 25,
    communityContribution: 10,
    objectives: [],
    timeline: { estimatedDuration: '7 days' },
    isCollaborative: false,
    currentParticipants: [],
    mentorAvailable: true
  },
  {
    id: '2',
    title: 'Gene Keys Integration Workshop',
    description: 'Deep dive into your primary Gene Keys and integrate their wisdom into daily life.',
    type: 'COMMUNITY',
    difficulty: 'INTERMEDIATE',
    minimumLevel: 'EXPANDING',
    status: 'AVAILABLE',
    progress: 0,
    experiencePoints: 300,
    consciousnessGrowth: 50,
    communityContribution: 30,
    objectives: [],
    timeline: { estimatedDuration: '14 days' },
    isCollaborative: true,
    currentParticipants: ['Alice', 'Bob', 'Charlie'],
    mentorAvailable: true
  },
  {
    id: '3',
    title: 'Sacred Geometry Creation',
    description: 'Create a sacred geometry artwork that represents your current consciousness level.',
    type: 'HYBRID',
    difficulty: 'ADVANCED',
    minimumLevel: 'INTEGRATING',
    status: 'LOCKED',
    progress: 0,
    experiencePoints: 500,
    consciousnessGrowth: 75,
    communityContribution: 50,
    objectives: [],
    timeline: { estimatedDuration: '21 days' },
    isCollaborative: false,
    currentParticipants: [],
    mentorAvailable: true
  }
])

const recentActivity = computed(() => [
  {
    id: '1',
    user: 'Sarah',
    action: 'completed the Sacred Breathwork Quest',
    type: 'Quest Complete',
    timestamp: new Date(Date.now() - 1000 * 60 * 30).toISOString()
  },
  {
    id: '2',
    user: 'Marcus',
    action: 'achieved Expanding consciousness level',
    type: 'Level Up',
    timestamp: new Date(Date.now() - 1000 * 60 * 60 * 2).toISOString()
  },
  {
    id: '3',
    user: 'Luna',
    action: 'shared a profound synchronicity',
    type: 'Synchronicity',
    timestamp: new Date(Date.now() - 1000 * 60 * 60 * 4).toISOString()
  },
  {
    id: '4',
    user: 'David',
    action: 'started the Gene Keys Mastery Quest',
    type: 'Quest Start',
    timestamp: new Date(Date.now() - 1000 * 60 * 60 * 6).toISOString()
  }
])

const todaysInsights = computed(() => [
  {
    id: '1',
    category: 'SELF_AWARENESS',
    insight: 'Your consciousness expands when you embrace uncertainty as a gateway to growth.'
  },
  {
    id: '2',
    category: 'RELATIONSHIPS',
    insight: 'Sacred connections arise when you see the divine spark in every interaction.'
  },
  {
    id: '3',
    category: 'PURPOSE',
    insight: 'Your unique gifts are the universe\'s way of expressing itself through you.'
  }
])

onMounted(() => {
  // Load user's sacred data
  consciousnessStore.loadUserData()
})

function formatTime(timestamp: string): string {
  const now = new Date()
  const time = new Date(timestamp)
  const diffMs = now.getTime() - time.getTime()
  const diffHours = Math.floor(diffMs / (1000 * 60 * 60))
  const diffMinutes = Math.floor(diffMs / (1000 * 60))
  
  if (diffHours > 0) {
    return `${diffHours}h ago`
  } else {
    return `${diffMinutes}m ago`
  }
}
</script>

<style scoped lang="scss">
@use '@/styles/main.scss' as *;

.sacred-home {
  max-width: 1400px;
  margin: 0 auto;
  padding: var(--spacing-lg);
  
  section {
    margin-bottom: var(--spacing-xl);
    
    .section-header {
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-bottom: var(--spacing-lg);
      
      h2 {
        font-size: 1.8rem;
        color: var(--text-light);
        margin: 0;
      }
      
      .view-all-btn {
        padding: var(--spacing-sm) var(--spacing-md);
        background: rgba(255, 255, 255, 0.1);
        border: 1px solid rgba(255, 255, 255, 0.2);
        border-radius: var(--border-radius);
        color: var(--text-light);
        text-decoration: none;
        transition: all 0.3s ease;
        
        &:hover {
          background: rgba(255, 255, 255, 0.2);
          transform: translateY(-2px);
        }
      }
    }
  }
  
  .quests-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: var(--spacing-lg);
    
    .quest-card {
      padding: var(--spacing-lg);
      background: rgba(255, 255, 255, 0.05);
      border-radius: var(--border-radius-lg);
      border: 1px solid rgba(255, 255, 255, 0.1);
      backdrop-filter: blur(10px);
      transition: transform 0.3s ease, box-shadow 0.3s ease;
      
      &:hover {
        transform: translateY(-4px);
        box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
      }
      
      &.beginner { border-left: 4px solid var(--consciousness-awakening); }
      &.intermediate { border-left: 4px solid var(--consciousness-expanding); }
      &.advanced { border-left: 4px solid var(--consciousness-integrating); }
      &.master { border-left: 4px solid var(--consciousness-mastering); }
      
      .quest-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: var(--spacing-md);
        
        .quest-type {
          font-size: 0.8rem;
          color: var(--sacred-gold);
          text-transform: uppercase;
          letter-spacing: 0.05em;
        }
        
        .quest-difficulty {
          font-size: 0.8rem;
          color: var(--text-muted);
          text-transform: uppercase;
        }
      }
      
      .quest-title {
        font-size: 1.2rem;
        color: var(--text-light);
        margin: 0 0 var(--spacing-sm) 0;
      }
      
      .quest-description {
        color: var(--text-muted);
        margin: 0 0 var(--spacing-md) 0;
        line-height: 1.5;
      }
      
      .quest-progress {
        margin-bottom: var(--spacing-md);
        
        .progress-bar {
          width: 100%;
          height: 6px;
          background: rgba(255, 255, 255, 0.1);
          border-radius: 3px;
          overflow: hidden;
          margin-bottom: var(--spacing-xs);
          
          .progress-fill {
            height: 100%;
            background: linear-gradient(90deg, var(--sacred-gold), var(--sacred-light));
            border-radius: 3px;
            transition: width 1s ease-out;
          }
        }
        
        .progress-text {
          font-size: 0.8rem;
          color: var(--text-muted);
        }
      }
      
      .quest-rewards {
        display: flex;
        gap: var(--spacing-sm);
        
        .reward-item {
          font-size: 0.8rem;
          padding: var(--spacing-xs) var(--spacing-sm);
          background: rgba(255, 215, 0, 0.1);
          color: var(--sacred-gold);
          border-radius: var(--border-radius);
          border: 1px solid rgba(255, 215, 0, 0.2);
        }
      }
    }
  }
  
  .activity-stream {
    .activity-item {
      display: flex;
      align-items: center;
      gap: var(--spacing-md);
      padding: var(--spacing-md);
      margin-bottom: var(--spacing-sm);
      background: rgba(255, 255, 255, 0.05);
      border-radius: var(--border-radius);
      border: 1px solid rgba(255, 255, 255, 0.1);
      
      .activity-avatar {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        background: var(--sacred-gold);
        color: var(--sacred-primary);
        display: flex;
        align-items: center;
        justify-content: center;
        font-weight: 600;
      }
      
      .activity-content {
        flex: 1;
        
        .activity-text {
          color: var(--text-light);
          margin-bottom: var(--spacing-xs);
        }
        
        .activity-time {
          font-size: 0.8rem;
          color: var(--text-muted);
        }
      }
      
      .activity-type {
        font-size: 0.8rem;
        padding: var(--spacing-xs) var(--spacing-sm);
        background: rgba(255, 255, 255, 0.1);
        border-radius: var(--border-radius);
        color: var(--text-muted);
      }
    }
  }
  
  .insights-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: var(--spacing-lg);
    
    .insight-card {
      padding: var(--spacing-lg);
      background: linear-gradient(135deg, rgba(255, 255, 255, 0.05), rgba(255, 255, 255, 0.02));
      border-radius: var(--border-radius-lg);
      border: 1px solid rgba(255, 255, 255, 0.1);
      backdrop-filter: blur(10px);
      
      .insight-category {
        font-size: 0.8rem;
        color: var(--sacred-gold);
        text-transform: uppercase;
        letter-spacing: 0.05em;
        margin-bottom: var(--spacing-sm);
      }
      
      .insight-text {
        color: var(--text-light);
        font-size: 1.1rem;
        line-height: 1.6;
        margin-bottom: var(--spacing-md);
        font-style: italic;
      }
      
      .insight-actions {
        display: flex;
        gap: var(--spacing-sm);
        
        .action-btn {
          padding: var(--spacing-xs) var(--spacing-sm);
          border: 1px solid rgba(255, 255, 255, 0.2);
          background: rgba(255, 255, 255, 0.05);
          color: var(--text-light);
          border-radius: var(--border-radius);
          cursor: pointer;
          transition: all 0.3s ease;
          font-size: 0.8rem;
          
          &:hover {
            background: rgba(255, 255, 255, 0.1);
            transform: translateY(-1px);
          }
          
          &.reflect {
            border-color: var(--consciousness-expanding);
            color: var(--consciousness-expanding);
          }
          
          &.integrate {
            border-color: var(--consciousness-integrating);
            color: var(--consciousness-integrating);
          }
        }
      }
    }
  }
}

@media (max-width: 768px) {
  .sacred-home {
    padding: var(--spacing-md);
    
    .quests-grid,
    .insights-grid {
      grid-template-columns: 1fr;
    }
    
    section .section-header {
      flex-direction: column;
      align-items: flex-start;
      gap: var(--spacing-sm);
    }
  }
}
</style>
