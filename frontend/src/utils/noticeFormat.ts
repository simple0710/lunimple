export const formatNoticeDate = (isoString: string): string => {
  const date = new Date(isoString);
  return `${date.getMonth() + 1}/${date.getDate()}`;
};
