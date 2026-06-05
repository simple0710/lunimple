import { useState } from 'react';
import { CONTEST_TYPES, PROBLEM_LABELS } from '../constants';
import { useContestProblems } from '../hooks/useContestProblems';
import type { ContestType } from '../types/contest';
import { getProblemLabelColor } from '../utils/difficultyColor';
import styles from './ProblemPage.module.css';

const codeLabel = (code: string) => (code === 'EX' ? 'H/Ex' : code);

export const ProblemPage = () => {
  const [selectedTypes, setSelectedTypes] = useState<ContestType[]>([]);
  const [page, setPage] = useState(1);
  const { contests, isLoading, error } = useContestProblems({
    page,
    size: 10,
  });

  const toggleType = (type: ContestType) => {
    setSelectedTypes((prev) =>
      prev.includes(type) ? prev.filter((t) => t !== type) : [...prev, type],
    );
    setPage(1);
  };

  const filteredContests =
    selectedTypes.length === 0
      ? contests
      : contests.filter((c) => selectedTypes.includes(c.contestType));

  const problemLabels = PROBLEM_LABELS;

  return (
    <div className={styles.page}>
      <div className={styles.filterBox}>
        <div className={styles.filterRow}>
          <input className={styles.handleInput} placeholder="handle1" readOnly />
        </div>
        <div className={styles.filterRow}>
          <div className={styles.typeButtons}>
            {CONTEST_TYPES.map((t) => (
              <button
                key={t}
                type="button"
                className={`${styles.typeBtn} ${selectedTypes.includes(t) ? styles.active : ''}`}
                onClick={() => toggleType(t)}
              >
                {t}
              </button>
            ))}
          </div>
          <div className={styles.checkboxGroup}>
            <label>
              <input type="checkbox" /> During Contest Solve
            </label>
            <label>
              <input type="checkbox" /> After Contest Solve
            </label>
          </div>
          <div className={styles.scoreFilter}>
            <div className={styles.scoreRow}>
              <span className={styles.statTotal}>TOTAL</span>
              <span className={styles.statAc}>AC</span>
              <span className={styles.statWa}>WA</span>
              <span className={styles.statNn}>NN</span>
            </div>
            <div className={styles.scoreRow}>
              <span className={styles.band1}>~400</span>
              <span className={styles.band2}>400~800</span>
              <span className={styles.band3}>800~1200</span>
              <span className={styles.band4}>1200+</span>
            </div>
          </div>
        </div>
      </div>

      {error && <p className={styles.error}>{error}</p>}
      {isLoading && <p className={styles.loading}>로딩 중...</p>}

      <div className={styles.tableWrapper}>
        <table className={styles.table}>
          <thead>
            <tr>
              <th>Contest</th>
              {problemLabels.map((p) => (
                <th key={p} style={{ color: getProblemLabelColor(p) }}>
                  {p}
                </th>
              ))}
            </tr>
          </thead>
          <tbody>
            {filteredContests.length === 0 && !isLoading && (
              <tr>
                <td colSpan={problemLabels.length + 1} className={styles.empty}>
                  표시할 대회가 없습니다.
                </td>
              </tr>
            )}
            {filteredContests.map((contest) => {
              const problemMap = new Map(
                contest.problems.map((p) => [codeLabel(p.code), p]),
              );
              return (
                <tr key={contest.contestName}>
                  <td className={styles.contestName}>
                    <a
                      href={contest.contestUrl}
                      target="_blank"
                      rel="noreferrer"
                    >
                      {contest.contestName}
                    </a>
                  </td>
                  {problemLabels.map((label) => {
                    const prob = problemMap.get(label);
                    return (
                      <td key={label} className={styles.problemCell}>
                        {prob ? (
                          <>
                            <div
                              className={styles.problemTitle}
                              style={{ color: getProblemLabelColor(label) }}
                            >
                              <a
                                href={prob.url}
                                target="_blank"
                                rel="noreferrer"
                                style={{ color: 'inherit' }}
                              >
                                {label} - {prob.title}
                              </a>
                            </div>
                            <div className={styles.problemScore}>
                              {prob.difficulty}
                            </div>
                          </>
                        ) : (
                          <span className={styles.noProblem}>-</span>
                        )}
                      </td>
                    );
                  })}
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>

      <div className={styles.pagination}>
        <button
          type="button"
          disabled={page <= 1}
          onClick={() => setPage((p) => p - 1)}
        >
          이전
        </button>
        <span>페이지 {page}</span>
        <button type="button" onClick={() => setPage((p) => p + 1)}>
          다음
        </button>
      </div>
    </div>
  );
};
