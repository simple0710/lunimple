import { useEffect, useState } from 'react';
import { fetchNotices } from '../api/notice';
import type { Notice, NoticeListParams } from '../types/notice';

export const useNotices = (params?: NoticeListParams) => {
  const [notices, setNotices] = useState<Notice[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const paramsKey = JSON.stringify(params ?? {});

  useEffect(() => {
    const load = async () => {
      try {
        setIsLoading(true);
        setError(null);
        const res = await fetchNotices(params);
        setNotices(res.data.content);
      } catch {
        setError('공지를 불러오지 못했습니다.');
      } finally {
        setIsLoading(false);
      }
    };
    load();
  }, [paramsKey]);

  return { notices, isLoading, error };
};
