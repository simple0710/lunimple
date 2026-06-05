import { useEffect, useState } from 'react';
import { fetchContestProblems } from '../api/contest';
import type { ContestProblemList, ContestProblemsParams } from '../types/problem';

export const useContestProblems = (params?: ContestProblemsParams) => {
  const [contests, setContests] = useState<ContestProblemList[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const paramsKey = JSON.stringify(params ?? {});

  useEffect(() => {
    const load = async () => {
      try {
        setIsLoading(true);
        setError(null);
        const res = await fetchContestProblems(params);
        setContests(res.data.content);
      } catch {
        setError('문제 목록을 불러오지 못했습니다.');
      } finally {
        setIsLoading(false);
      }
    };
    load();
  }, [paramsKey]);

  return { contests, isLoading, error };
};
