export type NoticeImportance = 'RECENT' | 'IMPORTANT' | 'NORMAL' | 'URGENT';

export interface Notice {
  id: number;
  title: string;
  url: string;
  importance: NoticeImportance;
  createdAt: string;
}

export interface NoticeDetail extends Notice {
  content: string;
}

export interface NoticeListParams {
  page?: number;
  size?: number;
  importance?: NoticeImportance;
}
