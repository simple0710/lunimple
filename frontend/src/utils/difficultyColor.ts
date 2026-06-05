/** 문제 난이도·컬럼별 표시 색 (목업 기준) */
export const getProblemLabelColor = (label: string): string => {
  switch (label) {
    case 'A':
    case 'B':
      return '#555555';
    case 'C':
      return '#8d5524';
    case 'D':
      return '#00acc1';
    case 'E':
      return '#2e7d32';
    case 'F':
      return '#f9a825';
    case 'G':
      return '#e53935';
    case 'H/Ex':
      return '#6a1b9a';
    default:
      return '#333333';
  }
};

export const getDifficultyColor = (difficulty: number): string => {
  if (difficulty >= 2000) return '#ff0000';
  if (difficulty >= 1600) return '#ff8000';
  if (difficulty >= 1200) return '#0000ff';
  if (difficulty >= 800) return '#008000';
  if (difficulty >= 400) return '#8d5524';
  return '#555555';
};
