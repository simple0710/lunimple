export type ContestType = 'ABC' | 'ARC' | 'AGC';

export interface Contest {
  name: string;
  contestType: ContestType;
  startTime: string;
  durationMinutes: number;
  ratedMin: number | null;
  ratedMax: number | null;
}

export interface ContestListParams {
  page?: number;
  size?: number;
  contestType?: ContestType;
}
