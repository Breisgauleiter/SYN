<template>
  <div class="quest-card" :class="[questStatusClass, questTypeClass]">
    <!-- Quest Header -->
    <header class="quest-header">
      <div class="quest-type-badge">
        <span class="type-icon">{{ questTypeIcon }}</span>
        <span class="type-text">{{ formatQuestType(quest.questType) }}</span>
      </div>
      <div class="quest-difficulty">
        <span class="difficulty-dots">
          <span 
            v-for="n in 10" 
            :key="n"
            class="difficulty-dot"
            :class="{ active: n <= quest.difficultyLevel }"
          ></span>
        </span>
        <span class="difficulty-text">Level {{ quest.difficultyLevel }}</span>
      </div>
    </header>

    <!-- Quest Content -->
    <div class="quest-content">
      <h3 class="quest-title">{{ quest.title }}</h3>
      <p class="quest-description">{{ truncatedDescription }}</p>
      
      <!-- Quest Metadata -->
      <div class="quest-metadata">
        <div class="metadata-item">
          <span class="metadata-icon">💼</span>
          <span class="metadata-text">{{ formatBusinessTrack(quest.businessTrack) }}</span>
        </div>
        <div class="metadata-item">
          <span class="metadata-icon">⏱️</span>
          <span class="metadata-text">{{ formatEstimatedHours(quest.estimatedHours) }}</span>
        </div>
        <div class="metadata-item">
          <span class="metadata-icon">✨</span>
          <span class="metadata-text">{{ formatExperiencePoints(quest.experiencePoints) }}</span>
        </div>
      </div>

      <!-- GitHub Integration Info -->
      <div v-if="quest.isLinkedToGitHub" class="github-info">
        <div class="github-link">
          <span class="github-icon">🔗</span>
          <a 
            :href="quest.githubUrl" 
            target="_blank" 
            class="github-url"
          >
            {{ quest.githubRepository }}#{{ quest.githubIssueNumber }}
          </a>
        </div>
      </div>

      <!-- Quest Progress (for active quests) -->
      <div v-if="quest.status === 'IN_PROGRESS'" class="quest-progress">
        <div class="progress-header">
          <span class="progress-label">Progress</span>
          <span class="progress-time">
            Started {{ formatRelativeTime(quest.startedAt) }}
          </span>
        </div>
        <div class="progress-bar">
          <div class="progress-fill" :style="{ width: '45%' }"></div>
        </div>
      </div>

      <!-- Quest Completion Info -->
      <div v-if="quest.status === 'COMPLETED'" class="quest-completion">
        <div class="completion-info">
          <span class="completion-icon">🎉</span>
          <span class="completion-text">
            Completed {{ formatRelativeTime(quest.completedAt) }}
          </span>
        </div>
        <div class="completion-stats">
          <span class="actual-hours">{{ quest.actualHours }}h actual</span>
          <span class="xp-earned">+{{ quest.experiencePoints }} XP</span>
        </div>
      </div>
    </div>

    <!-- Quest Actions -->
    <footer class="quest-actions">
      <div v-if="quest.status === 'PROPOSED'" class="proposed-actions">
        <button 
          @click="$emit('start', quest)"
          class="action-button primary"
          :disabled="!canStartQuest"
        >
          <span class="button-icon">🚀</span>
          Start Quest
        </button>
        <button 
          @click="showDetails = !showDetails"
          class="action-button secondary"
        >
          <span class="button-icon">👁️</span>
          Details
        </button>
      </div>

      <div v-if="quest.status === 'IN_PROGRESS'" class="active-actions">
        <button 
          @click="$emit('complete', quest)"
          class="action-button success"
        >
          <span class="button-icon">✅</span>
          Complete
        </button>
        <button 
          @click="$emit('cancel', quest)"
          class="action-button danger"
        >
          <span class="button-icon">❌</span>
          Cancel
        </button>
      </div>

      <div v-if="quest.status === 'COMPLETED'" class="completed-actions">
        <button 
          @click="viewCompletionDetails"
          class="action-button secondary"
        >
          <span class="button-icon">📊</span>
          View Results
        </button>
      </div>
    </footer>

    <!-- Expanded Details -->
    <div v-if="showDetails" class="quest-details-expanded">
      <div class="details-content">
        <h4>Quest Details</h4>
        <p>{{ quest.description }}</p>
        
        <div class="details-requirements">
          <h5>Requirements</h5>
          <ul>
            <li>SCL Level {{ quest.difficultyLevel }} or higher</li>
            <li>{{ formatBusinessTrack(quest.businessTrack) }} track alignment</li>
            <li v-if="quest.isLinkedToGitHub">GitHub repository access</li>
          </ul>
        </div>

        <div class="details-rewards">
          <h5>Sacred Rewards</h5>
          <ul>
            <li>{{ quest.experiencePoints }} Experience Points</li>
            <li>{{ formatBusinessTrack(quest.businessTrack) }} track progression</li>
            <li>Consciousness expansion through sacred work</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { questHelpers } from '@/services/contributingService'
import type { SacredQuest } from '@/types/contributing'

// Props
interface Props {
  quest: SacredQuest
  canStartQuest?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  canStartQuest: true
})

// Emits
defineEmits<{
  start: [quest: SacredQuest]
  complete: [quest: SacredQuest]
  cancel: [quest: SacredQuest]
}>()

// Local state
const showDetails = ref(false)

// Computed properties
const questStatusClass = computed(() => `quest-${props.quest.status.toLowerCase()}`)
const questTypeClass = computed(() => `type-${props.quest.questType.toLowerCase()}`)

const questTypeIcon = computed(() => 
  questHelpers.getQuestTypeIcon(props.quest.questType)
)

const truncatedDescription = computed(() => {
  const maxLength = 120
  if (props.quest.description.length <= maxLength) {
    return props.quest.description
  }
  return props.quest.description.substring(0, maxLength) + '...'
})

// Methods
const { 
  formatExperiencePoints, 
  formatEstimatedHours,
  getDifficultyColor
} = questHelpers

function formatQuestType(type: string): string {
  return type.replace('_QUEST', '').replace('_', ' ')
}

function formatBusinessTrack(track: string): string {
  return track.replace('_', ' ').toLowerCase()
    .replace(/\b\w/g, l => l.toUpperCase())
}

function formatRelativeTime(timestamp: string | undefined): string {
  if (!timestamp) return 'unknown'
  
  const now = new Date()
  const time = new Date(timestamp)
  const diffMs = now.getTime() - time.getTime()
  const diffHours = Math.floor(diffMs / (1000 * 60 * 60))
  const diffDays = Math.floor(diffHours / 24)
  
  if (diffDays > 0) {
    return `${diffDays} day${diffDays > 1 ? 's' : ''} ago`
  }
  if (diffHours > 0) {
    return `${diffHours} hour${diffHours > 1 ? 's' : ''} ago`
  }
  return 'recently'
}

function viewCompletionDetails() {
  // TODO: Navigate to detailed completion view
  console.log('Viewing completion details for quest:', props.quest.id)
}
</script>

<style scoped lang="scss">
@import '@/assets/scss/sacred-consciousness.scss';

.quest-card {
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(10px);
  border-radius: var(--sacred-border-radius);
  border: 1px solid rgba(255, 255, 255, 0.1);
  overflow: hidden;
  transition: all 0.3s ease;
  
  &:hover {
    transform: translateY(-4px);
    border-color: rgba(255, 255, 255, 0.2);
    background: rgba(255, 255, 255, 0.08);
  }
  
  &.quest-proposed {
    border-left: 4px solid var(--sacred-blue);
  }
  
  &.quest-in_progress {
    border-left: 4px solid var(--sacred-gold);
  }
  
  &.quest-completed {
    border-left: 4px solid var(--sacred-green);
  }
  
  &.type-platform_quest {
    .quest-type-badge {
      background: linear-gradient(135deg, #3b82f6, #1d4ed8);
    }
  }
  
  &.type-community_quest {
    .quest-type-badge {
      background: linear-gradient(135deg, #10b981, #047857);
    }
  }
  
  &.type-business_quest {
    .quest-type-badge {
      background: linear-gradient(135deg, #f59e0b, #d97706);
    }
  }
  
  &.type-leadership_quest {
    .quest-type-badge {
      background: linear-gradient(135deg, #8b5cf6, #7c3aed);
    }
  }
}

// Header
.quest-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 1.5rem 0;
  
  .quest-type-badge {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    padding: 0.25rem 0.75rem;
    border-radius: 15px;
    color: white;
    font-size: 0.85rem;
    font-weight: 600;
    
    .type-icon {
      font-size: 1rem;
    }
  }
  
  .quest-difficulty {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    
    .difficulty-dots {
      display: flex;
      gap: 2px;
      
      .difficulty-dot {
        width: 6px;
        height: 6px;
        border-radius: 50%;
        background: rgba(255, 255, 255, 0.2);
        
        &.active {
          background: var(--sacred-gold);
        }
      }
    }
    
    .difficulty-text {
      font-size: 0.8rem;
      color: var(--sacred-text-muted);
    }
  }
}

// Content
.quest-content {
  padding: 1rem 1.5rem;
  
  .quest-title {
    font-size: 1.3rem;
    font-weight: 600;
    color: var(--sacred-text-light);
    margin: 0 0 0.75rem 0;
    line-height: 1.4;
  }
  
  .quest-description {
    color: var(--sacred-text-muted);
    line-height: 1.5;
    margin: 0 0 1rem 0;
    font-size: 0.95rem;
  }
  
  .quest-metadata {
    display: flex;
    flex-wrap: wrap;
    gap: 1rem;
    margin-bottom: 1rem;
    
    .metadata-item {
      display: flex;
      align-items: center;
      gap: 0.5rem;
      font-size: 0.9rem;
      color: var(--sacred-text-muted);
      
      .metadata-icon {
        font-size: 1rem;
      }
    }
  }
  
  .github-info {
    margin-bottom: 1rem;
    
    .github-link {
      display: flex;
      align-items: center;
      gap: 0.5rem;
      
      .github-icon {
        color: var(--sacred-blue);
      }
      
      .github-url {
        color: var(--sacred-blue);
        text-decoration: none;
        font-size: 0.9rem;
        
        &:hover {
          text-decoration: underline;
        }
      }
    }
  }
  
  .quest-progress {
    margin-bottom: 1rem;
    
    .progress-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 0.5rem;
      
      .progress-label {
        font-weight: 600;
        color: var(--sacred-text-light);
      }
      
      .progress-time {
        font-size: 0.8rem;
        color: var(--sacred-text-muted);
      }
    }
    
    .progress-bar {
      height: 6px;
      background: rgba(255, 255, 255, 0.1);
      border-radius: 3px;
      overflow: hidden;
      
      .progress-fill {
        height: 100%;
        background: var(--sacred-gradient-primary);
        transition: width 0.3s ease;
      }
    }
  }
  
  .quest-completion {
    .completion-info {
      display: flex;
      align-items: center;
      gap: 0.5rem;
      margin-bottom: 0.5rem;
      
      .completion-icon {
        font-size: 1.2rem;
      }
      
      .completion-text {
        color: var(--sacred-green);
        font-weight: 600;
      }
    }
    
    .completion-stats {
      display: flex;
      gap: 1rem;
      font-size: 0.9rem;
      color: var(--sacred-text-muted);
      
      .xp-earned {
        color: var(--sacred-gold);
        font-weight: 600;
      }
    }
  }
}

// Actions
.quest-actions {
  padding: 1rem 1.5rem;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  
  .proposed-actions,
  .active-actions,
  .completed-actions {
    display: flex;
    gap: 0.75rem;
  }
  
  .action-button {
    flex: 1;
    padding: 0.75rem 1rem;
    border: none;
    border-radius: var(--sacred-border-radius);
    font-weight: 600;
    cursor: pointer;
    transition: all 0.2s ease;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 0.5rem;
    font-size: 0.9rem;
    
    .button-icon {
      font-size: 1rem;
    }
    
    &.primary {
      background: var(--sacred-gradient-primary);
      color: white;
      
      &:hover {
        transform: translateY(-1px);
        box-shadow: 0 4px 12px rgba(129, 140, 248, 0.4);
      }
      
      &:disabled {
        opacity: 0.5;
        cursor: not-allowed;
        transform: none;
      }
    }
    
    &.secondary {
      background: rgba(255, 255, 255, 0.1);
      color: var(--sacred-text-light);
      border: 1px solid rgba(255, 255, 255, 0.2);
      
      &:hover {
        background: rgba(255, 255, 255, 0.15);
        border-color: rgba(255, 255, 255, 0.3);
      }
    }
    
    &.success {
      background: linear-gradient(135deg, #10b981, #047857);
      color: white;
      
      &:hover {
        transform: translateY(-1px);
        box-shadow: 0 4px 12px rgba(16, 185, 129, 0.4);
      }
    }
    
    &.danger {
      background: linear-gradient(135deg, #ef4444, #dc2626);
      color: white;
      
      &:hover {
        transform: translateY(-1px);
        box-shadow: 0 4px 12px rgba(239, 68, 68, 0.4);
      }
    }
  }
}

// Expanded Details
.quest-details-expanded {
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  background: rgba(0, 0, 0, 0.2);
  
  .details-content {
    padding: 1.5rem;
    
    h4 {
      color: var(--sacred-gold);
      margin: 0 0 1rem 0;
      font-size: 1.1rem;
    }
    
    h5 {
      color: var(--sacred-text-light);
      margin: 1.5rem 0 0.5rem 0;
      font-size: 1rem;
    }
    
    p {
      color: var(--sacred-text-muted);
      line-height: 1.6;
      margin: 0 0 1rem 0;
    }
    
    ul {
      margin: 0;
      padding-left: 1.5rem;
      
      li {
        color: var(--sacred-text-muted);
        margin-bottom: 0.5rem;
        line-height: 1.4;
      }
    }
  }
}

@media (max-width: 768px) {
  .quest-header {
    flex-direction: column;
    gap: 1rem;
    align-items: flex-start;
  }
  
  .quest-metadata {
    flex-direction: column;
    gap: 0.5rem;
  }
  
  .proposed-actions,
  .active-actions {
    flex-direction: column;
  }
}
</style>
