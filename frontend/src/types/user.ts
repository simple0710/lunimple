export type RankingSortType = 'RATING' | 'WIN' | 'MATCH';

export type ProblemCode = 'A' | 'B' | 'C' | 'D' | 'E' | 'F' | 'G' | 'H' | 'EX';

export type RecommendationTier = 'ESSENTIAL' | 'ADDITIONAL' | 'CHALLENGE';

export interface UserProfile {
  handle: string;
  rating: number;
  rank: string;
  totalSolved: number;
  averageSolvedRating: number;
  averageSubmissionCount: number;
  successRate: number;
  waRatio: number;
}

export interface UserStreak {
  streak: number;
  activeDates: string[];
}

export interface ProblemStatistics {
  problem: ProblemCode;
  total: number;
  solved: number;
}

export interface ContestStatistics {
  contestType: string;
  problems: ProblemStatistics[];
}

export interface UserStatistics {
  statistics: ContestStatistics[];
}

export interface WeaknessItem {
  contestType: string;
  problemCode: ProblemCode;
  successRate: number;
  averageAttempt: number;
  recentFailCount: number;
  weaknessScore: number;
}

export interface UserWeakness {
  weaknesses: WeaknessItem[];
}

export interface ProblemRecommendation {
  tier: RecommendationTier;
  title: string;
  difficulty: number;
  url: string;
  hint: string;
}

export interface UserRecommendation {
  recommendations: ProblemRecommendation[];
  solvedCount: number;
  totalRecommendations: number;
}

export interface Ranking {
  id: number;
  handle: string;
  rating: number;
  highest: number;
  win: number;
  match: number;
  countryCode: string;
}

export interface RankingParams {
  page?: number;
  size?: number;
  sort?: RankingSortType;
}

export interface RankingRow {
  ranking: number;
  handle: string;
  rating: number;
  countryCode: string;
}
