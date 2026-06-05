import type { NoticeImportance } from '../types/notice';

export const getNoticeImportanceLabel = (
  importance: NoticeImportance,
): string => {
  switch (importance) {
    case 'IMPORTANT':
    case 'URGENT':
      return '중요';
    case 'RECENT':
      return '일반';
    case 'NORMAL':
    default:
      return '일반';
  }
};

export const isImportantNotice = (importance: NoticeImportance): boolean =>
  importance === 'IMPORTANT' || importance === 'URGENT';
