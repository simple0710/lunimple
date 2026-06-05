import { useEffect, useState } from 'react';
import { fetchPastContests, fetchUpcomingContests } from '../api/contest';
import type { Contest, ContestListParams } from '../types/contest';

export interface UseContestsOptions {
  past?: ContestListParams;
  upcoming?: ContestListParams;
}

export const useContests = (options?: UseContestsOptions) => {
  const [pastContests, setPastContests] = useState<Contest[]>([]);
  const [upcomingContests, setUpcomingContests] = useState<Contest[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const pastKey = JSON.stringify(options?.past ?? {});
  const upcomingKey = JSON.stringify(options?.upcoming ?? {});

  useEffect(() => {
    const load = async () => {
      try {
        setIsLoading(true);
        setError(null);
        const [past, upcoming] = await Promise.all([
          fetchPastContests(options?.past),
          fetchUpcomingContests(options?.upcoming),
        ]);
        setPastContests(past.data.content);
        setUpcomingContests(upcoming.data.content);
      } catch {
        setError('대회 정보를 불러오지 못했습니다.');
      } finally {
        setIsLoading(false);
      }
    };
    load();
  }, [pastKey, upcomingKey]);

  return { pastContests, upcomingContests, isLoading, error };
};
