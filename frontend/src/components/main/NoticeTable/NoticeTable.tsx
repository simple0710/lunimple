import type { Notice } from '../../../types/notice';
import { formatNoticeDate } from '../../../utils/noticeFormat';
import {
  getNoticeImportanceLabel,
  isImportantNotice,
} from '../../../utils/noticeLabel';
import styles from './NoticeTable.module.css';

interface Props {
  notices: Notice[];
  isLoading?: boolean;
}

export const NoticeTable = ({ notices, isLoading }: Props) => {
  if (isLoading) {
    return <p className={styles.loading}>로딩 중...</p>;
  }

  return (
    <table className={styles.table}>
      <tbody>
        {notices.length === 0 && (
          <tr>
            <td colSpan={3} className={styles.empty}>
              공지가 없습니다.
            </td>
          </tr>
        )}
        {notices.map((n) => (
          <tr key={n.id}>
            <td
              className={
                isImportantNotice(n.importance)
                  ? styles.important
                  : styles.normal
              }
            >
              {getNoticeImportanceLabel(n.importance)}
            </td>
            <td className={styles.title}>
              <a
                href={n.url}
                target="_blank"
                rel="noreferrer"
                className={styles.link}
              >
                {n.title}
              </a>
            </td>
            <td className={styles.date}>{formatNoticeDate(n.createdAt)}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};
