import { useMemo } from 'react';
import { useSearchParams } from 'react-router-dom';
import {
  PieChart,
  Pie,
  Cell,
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
  Legend,
} from 'recharts';
import { StreakHeatmap } from '../components/search/StreakHeatmap/StreakHeatmap';
import { useUserProfile } from '../hooks/useUserProfile';
import {
  getContestColor,
  getContestTypesFromStatistics,
  statisticsToBarData,
  statisticsToPieData,
} from '../utils/chartData';
import { getRecommendationLabel } from '../utils/recommendationLabel';
import styles from './SearchPage.module.css';

export const SearchPage = () => {
  const [searchParams] = useSearchParams();
  const handle = searchParams.get('handle');
  const {
    profile,
    streak,
    statistics,
    weaknesses,
    recommendations,
    isLoading,
    errors,
  } = useUserProfile(handle);

  const pieData = useMemo(
    () =>
      statistics?.statistics
        ? statisticsToPieData(statistics.statistics)
        : [],
    [statistics],
  );

  const barData = useMemo(
    () =>
      statistics?.statistics
        ? statisticsToBarData(statistics.statistics)
        : [],
    [statistics],
  );

  const contestTypes = useMemo(
    () =>
      statistics?.statistics
        ? getContestTypesFromStatistics(statistics.statistics)
        : [],
    [statistics],
  );

  if (!handle) {
    return (
      <div className={styles.page}>
        <p className={styles.hint}>헤더 또는 메인 페이지에서 핸들을 검색해 주세요.</p>
      </div>
    );
  }

  const streakData = streak ?? { streak: 0, activeDates: [] };

  return (
    <div className={styles.page}>
      {isLoading && <p className={styles.loading}>로딩 중...</p>}
      {errors.map((msg) => (
        <p key={msg} className={styles.warn}>
          {msg}
        </p>
      ))}

      <div className={styles.card}>
        <h2 className={styles.cardTitle}>🚀 INFO</h2>
        <div className={styles.infoLayout}>
          <div className={styles.profileCol}>
            <img src="/logo.png" alt="LUNIMPLE" className={styles.avatar} />
            <p className={styles.handleText}>{handle}</p>
            {profile && (
              <p className={styles.ratingText}>
                {profile.rating} / {profile.rank}
              </p>
            )}
          </div>

          <div className={styles.statsCol}>
            {profile ? (
              <div className={styles.statsGrid}>
                <div className={styles.statBox}>
                  <p className={styles.statLabel}>총 풀이 수</p>
                  <p className={styles.statValue}>{profile.totalSolved}</p>
                </div>
                <div className={styles.statBox}>
                  <p className={styles.statLabel}>성공률</p>
                  <p className={styles.statValue}>
                    {profile.successRate.toFixed(0)}%
                  </p>
                </div>
                <div className={styles.statBox}>
                  <p className={styles.statLabel}>평균 난이도</p>
                  <p className={styles.statValue}>
                    {Math.round(profile.averageSolvedRating)}
                  </p>
                </div>
                <div className={styles.statBox}>
                  <p className={styles.statLabel}>실패 패턴</p>
                  <p className={styles.statValue}>
                    WA 비율 {profile.waRatio.toFixed(0)}%
                  </p>
                  <p className={styles.statSub}>
                    평균 제출 {profile.averageSubmissionCount.toFixed(1)}회
                  </p>
                </div>
              </div>
            ) : (
              <p className={styles.empty}>프로필 정보 없음</p>
            )}
          </div>

          <div className={styles.streakCol}>
            <StreakHeatmap
              streak={streakData.streak}
              activeDates={streakData.activeDates}
            />
          </div>
        </div>
      </div>

      <div className={styles.card}>
        <h2 className={styles.cardTitle}>📊 통계 분석</h2>
        {pieData.length > 0 ? (
          <>
            <div className={styles.chartLayout}>
              <div className={styles.chartItem}>
                <ResponsiveContainer width="100%" height={260}>
                  <PieChart>
                    <Pie
                      data={pieData}
                      dataKey="value"
                      nameKey="name"
                      cx="50%"
                      cy="50%"
                      outerRadius={92}
                      label={({ name, percent }) =>
                        `${name} ${((percent ?? 0) * 100).toFixed(0)}%`
                      }
                    >
                      {pieData.map((entry, i) => (
                        <Cell
                          key={entry.name}
                          fill={getContestColor(entry.name, i)}
                        />
                      ))}
                    </Pie>
                    <Tooltip />
                    <Legend />
                  </PieChart>
                </ResponsiveContainer>
              </div>
              <div className={styles.chartItemWide}>
                <ResponsiveContainer width="100%" height={260}>
                  <BarChart data={barData} layout="vertical">
                    <XAxis type="number" />
                    <YAxis type="category" dataKey="label" width={44} />
                    <Tooltip />
                    <Legend />
                    {contestTypes.map((key, i) => (
                      <Bar
                        key={key}
                        dataKey={key}
                        stackId="a"
                        fill={getContestColor(key, i)}
                      />
                    ))}
                  </BarChart>
                </ResponsiveContainer>
              </div>
            </div>
            <p className={styles.insight}>
              {contestTypes[0] ?? 'ABC'} 비중 높음 → 고난이도 경험 부족
            </p>
          </>
        ) : (
          <p className={styles.empty}>통계 데이터가 없습니다.</p>
        )}
      </div>

      <div className={styles.card}>
        <h2 className={styles.cardTitle}>🎯 약점 분석</h2>
        {weaknesses && weaknesses.weaknesses.length > 0 ? (
          <div className={styles.weaknessLayout}>
            <div className={styles.weaknessSection}>
              <h3>약점</h3>
              <ul className={styles.weaknessList}>
                {weaknesses.weaknesses.slice(0, 4).map((w, i) => (
                  <li key={i}>
                    {w.contestType} {w.problemCode} 성공률{' '}
                    {w.successRate.toFixed(0)}% (평균 대비 -20%)
                  </li>
                ))}
                <li>→ 결론: 구현 정확도 부족</li>
              </ul>
            </div>
            <div className={styles.weaknessSection}>
              <h3>취약 패턴</h3>
              <ul className={styles.weaknessList}>
                {weaknesses.weaknesses.slice(0, 3).map((w, i) => (
                  <li key={i}>
                    최근 실패 {w.recentFailCount}회 · 평균 시도{' '}
                    {w.averageAttempt.toFixed(1)}회
                  </li>
                ))}
              </ul>
            </div>
          </div>
        ) : (
          <p className={styles.empty}>약점 데이터가 없습니다.</p>
        )}
      </div>

      <div className={styles.card}>
        <h2 className={styles.cardTitle}>🔥 문제 추천</h2>
        {recommendations ? (
          <>
            <p className={styles.recCount}>
              {recommendations.solvedCount} /{' '}
              {recommendations.totalRecommendations}
            </p>
            <div className={styles.recGrid}>
              {recommendations.recommendations.map((rec, i) => (
                <div key={`${rec.url}-${i}`} className={styles.recCard}>
                  <p className={styles.recType}>
                    {getRecommendationLabel(rec.tier)}
                  </p>
                  <p className={styles.recTitle}>{rec.title}</p>
                  <p className={styles.recHint}>[ {rec.hint} ]</p>
                  <p className={styles.recDiff}>난이도: {rec.difficulty}</p>
                  <a
                    href={rec.url}
                    target="_blank"
                    rel="noreferrer"
                    className={styles.recBtn}
                  >
                    풀기
                  </a>
                </div>
              ))}
            </div>
            {recommendations.recommendations.length === 0 && (
              <p className={styles.empty}>추천할 문제가 없습니다.</p>
            )}
          </>
        ) : (
          <p className={styles.empty}>추천 데이터를 불러오는 중...</p>
        )}
      </div>
    </div>
  );
};
