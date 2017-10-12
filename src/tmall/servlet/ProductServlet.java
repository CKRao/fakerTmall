package tmall.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.util.Page;

public class ProductServlet extends BaseBackServlet {

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category c = categoryDAO.get(cid);

		String name = request.getParameter("name");
		String subTitle = request.getParameter("subTitle");
		float orignalPrice = Float.parseFloat(request.getParameter("orignalPrice"));
		float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
		int stock = Integer.parseInt(request.getParameter("stock"));

		Product pd = new Product();

		pd.setCategory(c);
		pd.setName(name);
		pd.setSubTitle(subTitle);
		pd.setOrignalPrice(orignalPrice);
		pd.setPromotePrice(promotePrice);
		pd.setStock(stock);

		productDAO.add(pd);

		return "@admin_product_list?cid=" + cid;
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
	    Product p = productDAO.get(id);
	    productDAO.delete(id);
	    return "@admin_product_list?cid="+p.getCategory().getId();
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Product p = productDAO.get(id);
		request.setAttribute("p", p);
		return "admin/editProduct.jsp";
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category c = categoryDAO.get(cid);

		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String subTitle = request.getParameter("subTitle");
		float orignalPrice = Float.parseFloat(request.getParameter("orignalPrice"));
		float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
		int stock = Integer.parseInt(request.getParameter("stock"));

		Product pd = new Product();

		pd.setId(id);
		pd.setCategory(c);
		pd.setName(name);
		pd.setSubTitle(subTitle);
		pd.setOrignalPrice(orignalPrice);
		pd.setPromotePrice(promotePrice);
		pd.setStock(stock);

		productDAO.update(pd);
		return "@admin_product_list?cid=" + pd.getCategory().getId();
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category c = categoryDAO.get(cid);

		List<Product> ps = productDAO.list(cid, page.getStart(), page.getCount());

		int total = productDAO.getTotal(cid);
		page.setTotal(total);
		page.setParam("&cid=" + c.getId());

		request.setAttribute("ps", ps);
		request.setAttribute("c", c);
		request.setAttribute("page", page);

		return "admin/listProduct.jsp";
	}

	public String editPropertyValue(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Product p = productDAO.get(id);
		request.setAttribute("p", p);
		
		List<Property> pts= propertyDAO.list(p.getCategory().getId());
        propertyValueDAO.init(p);
        
        List<PropertyValue> pvs = propertyValueDAO.list(p.getId());
        
        request.setAttribute("pvs", pvs);
		return  "admin/editProductValue.jsp"; 
	}

	public String updatePropertyValue(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pvid = Integer.parseInt(request.getParameter("pvid"));
        String value = request.getParameter("value");
         
        PropertyValue pv =propertyValueDAO.get(pvid);
        pv.setValue(value);
        propertyValueDAO.update(pv);
        return "%success";
	}
}
