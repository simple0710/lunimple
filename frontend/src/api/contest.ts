import { buildUrl, fetchApi } from './client';
import type { ApiResponse, PageResponse } from '../types/api';
import type { Contest, ContestListParams } from '../types/contest';
import type { ContestProblemList, ContestProblemsParams } from '../types/problem';

function toContestParams(params?: ContestListParams) {
  return {
    page: params?.page ?? 1,
    size: params?.size ?? 10,
    contest_type: params?.contestType,
  };
}

export const fetchUpcomingContests = async (
  params?: ContestListParams,
): Promise<ApiResponse<PageResponse<Contest>>> => {
  return fetchApi(
    buildUrl('/contests/upcoming', toContestParams(params)),
  );
};

export const fetchPastContests = async (
  params?: ContestListParams,
): Promise<ApiResponse<PageResponse<Contest>>> => {
  return fetchApi(buildUrl('/contests/past', toContestParams(params)));
};

export const fetchContestProblems = async (
  params?: ContestProblemsParams,
): Promise<ApiResponse<PageResponse<ContestProblemList>>> => {
  return fetchApi(
    buildUrl('/contests/problems', {
      page: params?.page ?? 1,
      size: params?.size ?? 10,
    }),
  );
};
