import type { ContestStatistics } from '../types/user';

export interface PieChartItem {
  name: string;
  value: number;
}

export interface BarChartRow {
  label: string;
  [contestType: string]: string | number;
}

/** 대회 유형별 고정 색상 */
export const CONTEST_CHART_COLORS: Record<string, string> = {
  ABC: '#7c6cfc',
  ARC: '#ff6b6b',
  AGC: '#00bcd4',
  AHC: '#ffd700',
  DEFAULT: '#9e9e9e',
};

export const getContestColor = (contestType: string, index = 0): string => {
  return (
    CONTEST_CHART_COLORS[contestType] ??
    Object.values(CONTEST_CHART_COLORS)[index % 4]
  );
};

export const statisticsToPieData = (
  statistics: ContestStatistics[],
): PieChartItem[] =>
  statistics.map((s) => ({
    name: s.contestType,
    value: s.problems.reduce((sum, p) => sum + p.solved, 0),
  }));

export const statisticsToBarData = (
  statistics: ContestStatistics[],
): BarChartRow[] => {
  const codes = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'EX'] as const;
  return codes.map((code) => {
    const row: BarChartRow = { label: code === 'EX' ? 'H/Ex' : code };
    for (const stat of statistics) {
      const problem = stat.problems.find((p) => p.problem === code);
      row[stat.contestType] = problem?.solved ?? 0;
    }
    return row;
  });
};

export const getContestTypesFromStatistics = (
  statistics: ContestStatistics[],
): string[] => statistics.map((s) => s.contestType);
