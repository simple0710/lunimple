import type { RecommendationTier } from '../types/user';

export const getRecommendationLabel = (tier: RecommendationTier): string => {
  switch (tier) {
    case 'ESSENTIAL':
      return '🔥 필수';
    case 'ADDITIONAL':
      return '🚀 추가';
    case 'CHALLENGE':
      return '🎯 도전';
    default:
      return tier;
  }
};
