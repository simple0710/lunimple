/** Spring Data Page (ApiResponse.data) */
export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
  empty: boolean;
}

export interface ApiResponse<T> {
  code: string;
  message: string;
  data: T;
}

export interface PaginationParams {
  /** 백엔드 1-based 페이지 (기본 1) */
  page?: number;
  size?: number;
}
