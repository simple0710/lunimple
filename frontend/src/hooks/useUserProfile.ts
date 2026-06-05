import { useEffect, useState } from 'react';
import {
  fetchUserProfile,
  fetchUserRecommendations,
  fetchUserStatistics,
  fetchUserStreak,
  fetchUserWeaknesses,
} from '../api/user';
import type {
  UserProfile,
  UserRecommendation,
  UserStatistics,
  UserStreak,
  UserWeakness,
} from '../types/user';

const emptyStreak = (): UserStreak => ({ streak: 0, activeDates: [] });

export const useUserProfile = (handle: string | null) => {
  const [profile, setProfile] = useState<UserProfile | null>(null);
  const [streak, setStreak] = useState<UserStreak | null>(null);
  const [statistics, setStatistics] = useState<UserStatistics | null>(null);
  const [weaknesses, setWeaknesses] = useState<UserWeakness | null>(null);
  const [recommendations, setRecommendations] =
    useState<UserRecommendation | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [errors, setErrors] = useState<string[]>([]);

  useEffect(() => {
    if (!handle) return;

    const load = async () => {
      setIsLoading(true);
      setErrors([]);

      const results = await Promise.allSettled([
        fetchUserProfile(handle),
        fetchUserStreak(handle),
        fetchUserStatistics(handle),
        fetchUserWeaknesses(handle),
        fetchUserRecommendations(handle),
      ]);

      const failed: string[] = [];

      if (results[0].status === 'fulfilled') {
        setProfile(results[0].value.data);
      } else {
        failed.push('프로필');
      }

      if (results[1].status === 'fulfilled') {
        const data = results[1].value.data;
        setStreak({
          streak: data.streak ?? 0,
          activeDates: data.activeDates ?? [],
        });
      } else {
        failed.push('스트릭');
        setStreak(emptyStreak());
      }

      if (results[2].status === 'fulfilled') {
        setStatistics(results[2].value.data);
      } else {
        failed.push('통계');
      }

      if (results[3].status === 'fulfilled') {
        setWeaknesses(results[3].value.data);
      } else {
        failed.push('약점');
      }

      if (results[4].status === 'fulfilled') {
        setRecommendations(results[4].value.data);
      } else {
        failed.push('추천');
        setRecommendations({
          recommendations: [],
          solvedCount: 0,
          totalRecommendations: 0,
        });
      }

      if (failed.length > 0) {
        setErrors([`${failed.join(', ')} 정보를 불러오지 못했습니다.`]);
      }

      setIsLoading(false);
    };

    load();
  }, [handle]);

  return {
    profile,
    streak,
    statistics,
    weaknesses,
    recommendations,
    isLoading,
    errors,
  };
};
