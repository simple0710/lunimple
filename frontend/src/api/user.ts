import { buildUrl, fetchApi } from './client';
import type { ApiResponse, PageResponse } from '../types/api';
import type {
  Ranking,
  RankingParams,
  UserProfile,
  UserRecommendation,
  UserStatistics,
  UserStreak,
  UserWeakness,
} from '../types/user';

export const fetchUserProfile = async (
  handle: string,
): Promise<ApiResponse<UserProfile>> => {
  return fetchApi(buildUrl(`/users/${encodeURIComponent(handle)}`));
};

export const fetchUserStreak = async (
  handle: string,
): Promise<ApiResponse<UserStreak>> => {
  return fetchApi(buildUrl(`/users/${encodeURIComponent(handle)}/streak`));
};

export const fetchUserStatistics = async (
  handle: string,
): Promise<ApiResponse<UserStatistics>> => {
  return fetchApi(
    buildUrl(`/users/${encodeURIComponent(handle)}/statistics`),
  );
};

export const fetchUserWeaknesses = async (
  handle: string,
): Promise<ApiResponse<UserWeakness>> => {
  return fetchApi(
    buildUrl(`/users/${encodeURIComponent(handle)}/weaknesses`),
  );
};

export const fetchUserRecommendations = async (
  handle: string,
): Promise<ApiResponse<UserRecommendation>> => {
  return fetchApi(
    buildUrl(`/users/${encodeURIComponent(handle)}/recommendations`),
  );
};

export const fetchRankings = async (
  params?: RankingParams,
): Promise<ApiResponse<PageResponse<Ranking>>> => {
  return fetchApi(
    buildUrl('/ranking', {
      page: params?.page ?? 1,
      size: params?.size ?? 10,
      sort: params?.sort ?? 'RATING',
    }),
  );
};
