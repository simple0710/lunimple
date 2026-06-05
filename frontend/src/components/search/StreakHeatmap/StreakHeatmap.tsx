import { useMemo, useState } from 'react';
import styles from './StreakHeatmap.module.css';

type ViewMode = 'recent' | 'year';

interface Props {
  streak: number;
  activeDates: string[];
}

const DAY_MS = 24 * 60 * 60 * 1000;
const WEEKDAY_LABELS = ['S', 'M', 'T', 'W', 'T', 'F', 'S'];

const toDateKey = (date: Date): string => {
  const y = date.getFullYear();
  const m = String(date.getMonth() + 1).padStart(2, '0');
  const d = String(date.getDate()).padStart(2, '0');
  return `${y}-${m}-${d}`;
};

/** GitHub 스타일: 주(열) × 요일(행) */
const buildWeekGrid = (cells: { key: string; active: boolean }[]) => {
  if (cells.length === 0) return [];

  const first = new Date(cells[0].key);
  const padDays = first.getDay();
  const padded: ({ key: string; active: boolean } | null)[] = [
    ...Array.from({ length: padDays }, () => null),
    ...cells,
  ];

  const weeks: ({ key: string; active: boolean } | null)[][] = [];
  for (let i = 0; i < padded.length; i += 7) {
    weeks.push(padded.slice(i, i + 7));
  }
  const lastWeek = weeks[weeks.length - 1];
  if (lastWeek && lastWeek.length < 7) {
    weeks[weeks.length - 1] = [
      ...lastWeek,
      ...Array.from({ length: 7 - lastWeek.length }, () => null),
    ];
  }
  return weeks;
};

export const StreakHeatmap = ({ streak, activeDates }: Props) => {
  const currentYear = new Date().getFullYear();
  const [viewMode, setViewMode] = useState<ViewMode>('recent');
  const [selectedYear, setSelectedYear] = useState(currentYear);

  const activeSet = useMemo(() => new Set(activeDates), [activeDates]);

  const availableYears = useMemo(() => {
    const years = new Set<number>([currentYear]);
    activeDates.forEach((d) => years.add(new Date(d).getFullYear()));
    return Array.from(years).sort((a, b) => b - a);
  }, [activeDates, currentYear]);

  const cells = useMemo(() => {
    const result: { key: string; active: boolean }[] = [];

    if (viewMode === 'recent') {
      const today = new Date();
      today.setHours(0, 0, 0, 0);
      for (let i = 364; i >= 0; i--) {
        const date = new Date(today.getTime() - i * DAY_MS);
        const key = toDateKey(date);
        result.push({ key, active: activeSet.has(key) });
      }
      return result;
    }

    const start = new Date(selectedYear, 0, 1);
    const end = new Date(selectedYear, 11, 31);
    for (
      let date = new Date(start);
      date <= end;
      date = new Date(date.getTime() + DAY_MS)
    ) {
      const key = toDateKey(date);
      result.push({ key, active: activeSet.has(key) });
    }
    return result;
  }, [viewMode, selectedYear, activeSet]);

  const weeks = useMemo(() => buildWeekGrid(cells), [cells]);

  const rangeLabel =
    viewMode === 'recent' ? '최근 365일' : `${selectedYear}년`;

  return (
    <div className={styles.wrapper}>
      <div className={styles.header}>
        <p className={styles.streakLabel}>
          스트릭 <strong>{streak}일</strong>
        </p>
        <div className={styles.controls}>
          <button
            type="button"
            className={`${styles.modeBtn} ${viewMode === 'recent' ? styles.modeActive : ''}`}
            onClick={() => setViewMode('recent')}
          >
            최근
          </button>
          <button
            type="button"
            className={`${styles.modeBtn} ${viewMode === 'year' ? styles.modeActive : ''}`}
            onClick={() => setViewMode('year')}
          >
            연도
          </button>
          {viewMode === 'year' && (
            <select
              className={styles.yearSelect}
              value={selectedYear}
              onChange={(e) => setSelectedYear(Number(e.target.value))}
              aria-label="연도 선택"
            >
              {availableYears.map((y) => (
                <option key={y} value={y}>
                  {y}년
                </option>
              ))}
            </select>
          )}
        </div>
      </div>
      <p className={styles.rangeLabel}>({rangeLabel})</p>

      <div className={styles.heatmap}>
        <div className={styles.weekdayCol}>
          {WEEKDAY_LABELS.map((label, i) => (
            <span key={`${label}-${i}`} className={styles.weekday}>
              {label}
            </span>
          ))}
        </div>
        <div className={styles.weeks}>
          {weeks.map((week, wi) => (
            <div key={wi} className={styles.weekCol}>
              {week.map((cell, di) =>
                cell ? (
                  <div
                    key={cell.key}
                    className={`${styles.cell} ${cell.active ? styles.active : ''}`}
                    title={cell.key}
                  />
                ) : (
                  <div key={`empty-${wi}-${di}`} className={styles.cellEmpty} />
                ),
              )}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};
