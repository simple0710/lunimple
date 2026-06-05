export const getRatingColor = (rating: number): string => {
  if (rating >= 2800) return '#ff0000';
  if (rating >= 2400) return '#ff8000';
  if (rating >= 2000) return '#ffff00';
  if (rating >= 1600) return '#0000ff';
  if (rating >= 1200) return '#00c0c0';
  if (rating >= 800) return '#008000';
  if (rating >= 400) return '#804000';
  return '#808080';
};
