package fr.excilys.pagination;


import java.util.List;


public class Page<T> {
  private List<T> content;
  private int pageId;
  private boolean previous;
  private boolean next;

  /**
   * Constructs a Page object with the parameters.
   * @param content  The page content (generally, a list of entities)
   * @param pageId   (the current page id)
   * @param previous true if the page is not the first in the page list, false if not
   * @param next     true if the page is not the last in the page list, false if not
   */
  public Page(final List<T> content, final int pageId, final boolean previous, final boolean next) {
    this.content = content;
    this.pageId = pageId;
    this.previous = previous;
    this.next = next;
  }

  public List<T> getContent() {
    return content;
  }

  public final void setContent(final List<T> content) {
    this.content = content;
  }

  public final int getPageId() {
    return pageId;
  }

  public final void setPageId(final int pageId) {
    this.pageId = pageId;
  }

  public final boolean isPrevious() {
    return previous;
  }

  public final void setPrevious(final boolean previous) {
    this.previous = previous;
  }

  public final boolean isNext() {
    return next;
  }

  public final void setNext(final boolean next) {
    this.next = next;
  }

  @Override
  public String toString() {
    return "Page [content=" + content + ", pageId=" + pageId + ", previous=" + previous + ", next="
        + next + "]";
  }

  /**
   * Create the dto page from a entity page.
   * @param mapper The entity mapper
   * @return The page
   */
//  public Page<ComputerDTO> createDtoPage(ComputerMapper mapper) {
//    List<ComputerDTO> data = new ArrayList<>();
//    for (T entity : getContent()) {
//      data.add(mapper.mapFromEntity(entity));
//    }
//
//    return new Page<IDto<T>>(data, pageId, previous, next);
//  }
}