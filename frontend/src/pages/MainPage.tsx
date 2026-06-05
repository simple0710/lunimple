import { useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import { SearchBar } from '../components/common/SearchBar/SearchBar';
import { ContestList } from '../components/main/ContestList/ContestList';
import { DailyProblemTable } from '../components/main/DailyProblemTable/DailyProblemTable';
import { NoticeTable } from '../components/main/NoticeTable/NoticeTable';
import { RankingTable } from '../components/main/RankingTable/RankingTable';
import { useContestProblems } from '../hooks/useContestProblems';
import { useContests } from '../hooks/useContests';
import { useNotices } from '../hooks/useNotices';
import { useRankings } from '../hooks/useRankings';
import styles from './MainPage.module.css';

export const MainPage = () => {
  const navigate = useNavigate();
  const { pastContests, upcomingContests, isLoading: contestsLoading } =
    useContests({
      past: { page: 1, size: 8 },
      upcoming: { page: 1, size: 8 },
    });
  const { rankings, isLoading: rankingsLoading } = useRankings({
    page: 1,
    size: 10,
    sort: 'RATING',
  });
  const { notices: allNotices, isLoading: noticesLoading } = useNotices({
    page: 1,
    size: 20,
  });
  const { contests: dailyContests, isLoading: problemsLoading } =
    useContestProblems({ page: 1, size: 1 });

  const importantNotices = useMemo(
    () =>
      allNotices.filter(
        (n) => n.importance === 'IMPORTANT' || n.importance === 'URGENT',
      ),
    [allNotices],
  );

  const recentNotices = useMemo(
    () => allNotices.filter((n) => n.importance === 'RECENT'),
    [allNotices],
  );

  const displayNotices = useMemo(() => {
    const merged = [...importantNotices, ...recentNotices];
    if (merged.length > 0) return merged;
    return allNotices;
  }, [importantNotices, recentNotices, allNotices]);

  const handleSearch = (handle: string) => {
    navigate(`/search?handle=${encodeURIComponent(handle)}`);
  };

  const contestBlockLoading = contestsLoading || rankingsLoading;

  return (
    <div className={styles.page}>
      <section className={styles.searchSection}>
        <SearchBar
          onSearch={handleSearch}
          // recentSearches={['simple710', 'tourist']}
        />
      </section>

      <section className={styles.topSection}>
        <div className={styles.noticeBox}>
          <h3 className={styles.sectionTitle}>공지</h3>
          <NoticeTable notices={displayNotices} isLoading={noticesLoading} />
        </div>
        <div className={styles.dailyProblem}>
          <h3 className={styles.sectionTitle}>오늘의 문제</h3>
          <DailyProblemTable
            contests={dailyContests}
            isLoading={problemsLoading}
          />
        </div>
      </section>

      <section className={styles.contestSection}>
        {contestBlockLoading ? (
          <p className={styles.loading}>로딩 중...</p>
        ) : (
          <>
            <ContestList title="다가올 대회" contests={upcomingContests} />
            <ContestList title="지난 대회" contests={pastContests} />
            <RankingTable rankings={rankings} />
          </>
        )}
      </section>

      <section className={styles.featureSection}>
        <div
          className={styles.featureCard}
          onClick={() => navigate('/problem')}
          onKeyDown={(e) => e.key === 'Enter' && navigate('/problem')}
          role="button"
          tabIndex={0}
        >
          <span className={styles.icon}>📊</span>
          <p> 통계 분석 </p>
        </div>
        <div
          className={styles.featureCard}
          onClick={() => navigate('/search?handle=simple710')}
          onKeyDown={(e) =>
            e.key === 'Enter' && navigate('/search?handle=simple710')
          }
          role="button"
          tabIndex={0}
        >
          <span className={styles.icon}>🎯</span>
          <p> 약점 분석 </p>
        </div>
        <div
          className={styles.featureCard}
          onClick={() => navigate('/search?handle=simple710')}
          onKeyDown={(e) =>
            e.key === 'Enter' && navigate('/search?handle=simple710')
          }
          role="button"
          tabIndex={0}
        >
          <span className={styles.icon}>⭐</span>
          <p> 문제 추천 </p>
        </div>
      </section>

      <p className={styles.tagline}>
        &quot;AtCoder 데이터를 기반으로 당신의 약점을 분석하고 문제를 추천합니다&quot;
      </p>
    </div>
  );
};
