//package fr.excilys.pagination;
//
//
//import java.util.List;
//
//import fr.excilys.exceptions.ComputerDAOException;
//
//
//
//public class Page {
//
//  
//	private final static String LIMIT_DEFAULT = "10";
//	private final static String OFFSET_DEFAULT = "1";
//	private final static Integer[] LIMIT_VALID = { 10, 20, 50, 100 };
//
//  /**
//   * Constructs a Page object with the parameters.
//   * @param content  The page content (generally, a list of entities)
//   * @param pageId   (the current page id)
//   * @param previous true if the page is not the first in the page list, false if not
//   * @param next     true if the page is not the last in the page list, false if not
//   */
//  public Page() {
//
//  }
//	public Long getMaxPage() throws ComputerDAOException {
//		Long returnPageMax = null;
//		returnPageMax = this.computerSer.getCountRow();
//		return returnPageMax;
//	}
//
//	public Integer getPagination(String value, int defaultValue) {
//		Integer returnValue = null;
//		if (isNotNullorEmpty(value) && valideInt(value)) {
//			returnValue = Integer.parseInt(value);
//		} else {
//			returnValue = defaultValue;
//		}
//		return returnValue;
//	}
//
//	public static boolean isNotNullorEmpty(String value) {
//		boolean isNull = false;
//		if (value != null && !value.isEmpty()) {
//			isNull = true;
//		}
//		return isNull;
//	}
//
//	private boolean valideInt(String value) {
//		boolean isValide = false;
//		try {
//			Integer.parseInt(value);
//			isValide = true;
//			return isValide;
//		} catch (NumberFormatException e) {
//			return isValide;
//		}
//	}
//
//	private static Long getPageNumberMax(Long nbrRow, int limit) {
//		return (long) Math.ceil((1.0 * nbrRow) / limit);
//	}
//
//	// TODO mettre dans un validator
//	private void valideLimit(int limit) throws NumberFormatException {
//		boolean isValide = false;
//		for (int i = 0; i < LIMIT_VALID.length; i++) {
//			if (LIMIT_VALID[i].equals(limit)) {
//				isValide = true;
//			}
//		}
//		if (!isValide) {
//			throw new NumberFormatException();
//		}
//	}
//
//	private void validePage(int page, Long maxPage) throws NumberFormatException {
//		if (page > maxPage || page < 1) {
//			throw new NumberFormatException();
//		}
//	}
//
// 
//
//
//
//  /**
//   * Create the dto page from a entity page.
//   * @param mapper The entity mapper
//   * @return The page
//   */
////  public Page<ComputerDTO> createDtoPage(ComputerMapper mapper) {
////    List<ComputerDTO> data = new ArrayList<>();
////    for (T entity : getContent()) {
////      data.add(mapper.mapFromEntity(entity));
////    }
////
////    return new Page<IDto<T>>(data, pageId, previous, next);
////  }
//}