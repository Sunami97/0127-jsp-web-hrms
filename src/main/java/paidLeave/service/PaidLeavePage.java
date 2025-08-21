package paidLeave.service;

import paidLeave.model.PaidLeave;

import java.util.List;

public class PaidLeavePage {
    private int total;
    private int currentPage;
    private List<PaidLeave> content;
    private int totalPages;
    private int startPage;
    private int endPage;

    public PaidLeavePage(int total, int currentPage, int size, List<PaidLeave> content) {
        this.total = total;
        this.currentPage = currentPage;
        this.content = content; // 現在ページに表示する有給休暇申請のリスト
        if (total == 0) {
            // データが存在しない場合、ページ情報をすべて 0 に設定
            totalPages = 0;
            startPage = 0;
            endPage = 0;
        } else {
            // 総ページ数を計算（1ページあたりの件数で割る）
            totalPages = total / size;
            // 余りがある場合は 1 ページ追加
            if (total % size > 0) {
                totalPages++;
            }
            // ページ番号を 5 ページ単位でグループ化
            int modVal = currentPage % 5;
            startPage = currentPage / 5 * 5 +1;

            if (modVal == 0)
                startPage -= 5; // 5の倍数ページの場合は開始ページを調整

            // 表示する最後のページ番号を計算（開始ページ + 4）
            endPage = startPage + 4;

            if (endPage > totalPages)
                endPage = totalPages;
        }
    }

    public int getTotal() {
        return total;
    }

    public boolean hasNoPaidLeaves() {
        return total == 0;
    }

    public boolean hasPaidLeaves() {
        return total > 0;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<PaidLeave> getContent() {
        return content;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }
}
