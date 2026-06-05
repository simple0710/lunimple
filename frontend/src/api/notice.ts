import { buildUrl, fetchApi } from './client';
import type { ApiResponse, PageResponse } from '../types/api';
import type { Notice, NoticeDetail, NoticeListParams } from '../types/notice';

export const fetchNotices = async (
  params?: NoticeListParams,
): Promise<ApiResponse<PageResponse<Notice>>> => {
  return fetchApi(
    buildUrl('/notices', {
      page: params?.page ?? 1,
      size: params?.size ?? 10,
      importance: params?.importance,
    }),
  );
};

export const fetchNoticeDetail = async (
  id: number,
): Promise<ApiResponse<NoticeDetail>> => {
  return fetchApi(buildUrl(`/notices/${id}`));
};
