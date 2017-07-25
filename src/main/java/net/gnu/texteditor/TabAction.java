package net.gnu.texteditor;

public interface TabAction {
	public void closeCurTab();
	public void closeOtherTabs();
	public void addTab(Object...objs);
	public int getSize();
}
