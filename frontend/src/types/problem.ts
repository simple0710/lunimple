import type { ContestType } from './contest';
import type { ProblemCode } from './user';

export interface Problem {
  code: ProblemCode;
  title: string;
  difficulty: number;
  url: string;
}

export interface ContestProblemList {
  contestName: string;
  contestUrl: string;
  contestType: ContestType;
  problems: Problem[];
}

export interface ContestProblemsParams {
  page?: number;
  size?: number;
}
