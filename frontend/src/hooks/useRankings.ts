import { useEffect, useState } from 'react';
import { fetchRankings } from '../api/user';
import type { RankingParams, RankingRow } from '../types/user';

export const useRankings = (params?: RankingParams) => {
  const [rankings, setRankings] = useState<RankingRow[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const paramsKey = JSON.stringify(params ?? {});

  useEffect(() => {
    const load = async () => {
      try {
        setIsLoading(true);
        setError(null);
        const page = params?.page ?? 1;
        const size = params?.size ?? 10;
        const res = await fetchRankings({ ...params, page, size });
        const baseRank = (page - 1) * size;
        setRankings(
          res.data.content.map((r, i) => ({
            ranking: baseRank + i + 1,
            handle: r.handle,
            rating: r.rating,
            countryCode: r.countryCode,
          })),
        );
      } catch {
        setError('순위 정보를 불러오지 못했습니다.');
      } finally {
        setIsLoading(false);
      }
    };
    load();
  }, [paramsKey]);

  return { rankings, isLoading, error };
};
