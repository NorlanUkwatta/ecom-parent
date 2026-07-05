<%@ page import="java.util.List" %>
<%@ page import="com.techmart.entity.Category" %>
<%@ page import="com.techmart.entity.Color" %>
<%@ page import="com.techmart.entity.Storage" %>
<%@ page import="com.techmart.entity.Brand" %>

<%-- 1. ADD PRODUCT MODAL --%>
<div class="modal fade" id="addProductModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-dark text-white">
                <h5 class="modal-title">Add New Product</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <form action="${pageContext.request.contextPath}/admin/products" method="POST" enctype="multipart/form-data">
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-7">
                            <div class="mb-3"><label class="form-label fw-bold">Product Name</label><input type="text" class="form-control" name="name" required></div>
                            <div class="mb-3"><label class="form-label fw-bold">Description</label><textarea class="form-control" name="description" rows="4"></textarea></div>
                            <div class="row">
                                <div class="col-6 mb-3"><label class="form-label fw-bold">Price ($)</label><input type="number" step="0.01" class="form-control" name="price" required></div>
                                <div class="col-6 mb-3"><label class="form-label fw-bold">Stock</label><input type="number" class="form-control" name="stock" required></div>
                            </div>
                        </div>
                        <div class="col-md-5 bg-light p-3 rounded border">
                            <h6 class="fw-bold mb-3 border-bottom pb-2">Specifications</h6>
                            <div class="mb-3">
                                <label class="form-label text-muted small">Category</label>
                                <select class="form-select" name="categoryId">
                                    <option value="">None</option>
                                    <% List<Category> cats = (List<Category>) request.getAttribute("categories"); if (cats != null) { for (Category c : cats) { %> <option value="<%= c.getId() %>"><%= c.getName() %></option> <% } } %>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label class="form-label text-muted small">Brand</label>
                                <select class="form-select" name="brandId">
                                    <option value="">None</option>
                                    <% List<Brand> brands = (List<Brand>) request.getAttribute("brands"); if (brands != null) {for (Brand b :brands){ %> <option value="<%= b.getId() %>"><%= b.getName() %></option> <%}}%>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label class="form-label text-muted small">Color</label>
                                <select class="form-select" name="colorId">
                                    <option value="">None</option>
                                    <% List<Color> cols = (List<Color>) request.getAttribute("colors"); if (cols != null) { for (Color c : cols) { %> <option value="<%= c.getId() %>"><%= c.getName() %></option> <% } } %>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label class="form-label text-muted small">Storage</label>
                                <select class="form-select" name="storageId">
                                    <option value="">None</option>
                                    <% List<Storage> stors = (List<Storage>) request.getAttribute("storages"); if (stors != null) { for (Storage s : stors) { %> <option value="<%= s.getId() %>"><%= s.getCapacity() %></option> <% } } %>
                                </select>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <h6 class="fw-bold mb-3">Product Images</h6>
                    <div class="row g-3">
                        <div class="col-md-3"><div class="card p-2 text-center"><img id="add-prev1" src="https://placehold.co/400?text=Main" class="img-fluid rounded mb-2"><input type="file" class="form-control form-control-sm" name="image1" required onchange="previewImage(this, 'add-prev1')"></div></div>
                        <div class="col-md-3"><div class="card p-2 text-center"><img id="add-prev2" src="https://placehold.co/400?text=Img+2" class="img-fluid rounded mb-2"><input type="file" class="form-control form-control-sm" name="image2" onchange="previewImage(this, 'add-prev2')"></div></div>
                        <div class="col-md-3"><div class="card p-2 text-center"><img id="add-prev3" src="https://placehold.co/400?text=Img+3" class="img-fluid rounded mb-2"><input type="file" class="form-control form-control-sm" name="image3" onchange="previewImage(this, 'add-prev3')"></div></div>
                        <div class="col-md-3"><div class="card p-2 text-center"><img id="add-prev4" src="https://placehold.co/400?text=Img+4" class="img-fluid rounded mb-2"><input type="file" class="form-control form-control-sm" name="image4" onchange="previewImage(this, 'add-prev4')"></div></div>
                    </div>
                </div>
                <div class="modal-footer bg-light">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Save Product</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%-- 2. EDIT PRODUCT MODAL --%>
<div class="modal fade" id="editProductModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-dark text-white">
                <h5 class="modal-title">Edit Product</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <form action="${pageContext.request.contextPath}/admin/products/edit" method="POST" enctype="multipart/form-data">
                <div class="modal-body">
                    <input type="hidden" name="id" id="edit-id">
                    <div class="row">
                        <div class="col-md-7">
                            <div class="mb-3"><label class="form-label fw-bold">Product Name</label><input type="text" class="form-control" name="name" id="edit-name" required></div>
                            <div class="mb-3"><label class="form-label fw-bold">Description</label><textarea class="form-control" name="description" id="edit-desc" rows="4"></textarea></div>
                            <div class="row">
                                <div class="col-6 mb-3"><label class="form-label fw-bold">Price ($)</label><input type="number" step="0.01" class="form-control" name="price" id="edit-price" required></div>
                                <div class="col-6 mb-3"><label class="form-label fw-bold">Stock</label><input type="number" class="form-control" name="stock" id="edit-stock" required></div>
                            </div>
                        </div>
                        <div class="col-md-5 bg-light p-3 rounded border">
                            <h6 class="fw-bold mb-3 border-bottom pb-2">Specifications</h6>
                            <div class="mb-3">
                                <label class="form-label text-muted small">Category</label>
                                <select class="form-select" name="categoryId" id="edit-category">
                                    <option value="">None</option>
                                    <% if (cats != null) { for (Category c : cats) { %> <option value="<%= c.getId() %>"><%= c.getName() %></option> <% } } %>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label class="form-label text-muted small">Brand</label>
                                <select class="form-select" name="brandId" id="edit-brand">
                                    <option value="">None</option>
                                    <%
                                        brands = (List<Brand>) request.getAttribute("brands");
                                        if (brands != null) {
                                            for (Brand b : brands) {
                                    %>
                                    <option value="<%= b.getId() %>"><%= b.getName() %></option>
                                    <% } } %>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label class="form-label text-muted small">Color</label>
                                <select class="form-select" name="colorId" id="edit-color">
                                    <option value="">None</option>
                                    <% if (cols != null) { for (Color c : cols) { %> <option value="<%= c.getId() %>"><%= c.getName() %></option> <% } } %>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label class="form-label text-muted small">Storage</label>
                                <select class="form-select" name="storageId" id="edit-storage">
                                    <option value="">None</option>
                                    <% if (stors != null) { for (Storage s : stors) { %> <option value="<%= s.getId() %>"><%= s.getCapacity() %></option> <% } } %>
                                </select>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <h6 class="fw-bold mb-3">Update Images</h6>
                    <div class="row g-3">
                        <div class="col-md-3"><div class="card p-2 text-center"><img id="edit-prev1" src="" class="img-fluid rounded mb-2"><input type="file" class="form-control form-control-sm" name="image1" onchange="previewImage(this, 'edit-prev1')"></div></div>
                        <div class="col-md-3"><div class="card p-2 text-center"><img id="edit-prev2" src="" class="img-fluid rounded mb-2"><input type="file" class="form-control form-control-sm" name="image2" onchange="previewImage(this, 'edit-prev2')"></div></div>
                        <div class="col-md-3"><div class="card p-2 text-center"><img id="edit-prev3" src="" class="img-fluid rounded mb-2"><input type="file" class="form-control form-control-sm" name="image3" onchange="previewImage(this, 'edit-prev3')"></div></div>
                        <div class="col-md-3"><div class="card p-2 text-center"><img id="edit-prev4" src="" class="img-fluid rounded mb-2"><input type="file" class="form-control form-control-sm" name="image4" onchange="previewImage(this, 'edit-prev4')"></div></div>
                    </div>
                </div>
                <div class="modal-footer bg-light">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Save Changes</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%-- ADD CATEGORY MODAL --%>
<div class="modal fade" id="addCategoryModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content border-0 shadow">
            <div class="modal-header border-bottom">
                <h5 class="modal-title fw-bold"><i class="bi bi-tags me-2 text-primary"></i> Add Category</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <form action="${pageContext.request.contextPath}/admin/categories" method="POST">
                <div class="modal-body p-4">
                    <div class="mb-2">
                        <label class="form-label fw-bold text-muted small text-uppercase">Category Name</label>
                        <input type="text" class="form-control form-control-lg fs-6" name="name"
                               placeholder="e.g. Laptops" required>
                    </div>
                </div>
                <div class="modal-footer bg-light border-top-0">
                    <button type="button" class="btn btn-white border" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary px-4">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%-- ADD COLOR MODAL --%>
<div class="modal fade" id="addColorModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content border-0 shadow">
            <div class="modal-header border-bottom">
                <h5 class="modal-title fw-bold"><i class="bi bi-palette me-2 text-danger"></i> Add Color</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <form action="${pageContext.request.contextPath}/admin/colors" method="POST">
                <div class="modal-body p-4">
                    <div class="mb-2">
                        <label class="form-label fw-bold text-muted small text-uppercase">Color Name</label>
                        <input type="text" class="form-control form-control-lg fs-6" name="name"
                               placeholder="e.g. Midnight Black" required>
                    </div>
                </div>

                <div class="modal-body p-4">
                    <div class="mb-2">
                        <label class="form-label fw-bold text-muted small text-uppercase">HexCode</label>
                        <input type="text" class="form-control form-control-lg fs-6" name="hexcode"
                               placeholder="e.g. #000000">
                    </div>
                </div>
                <div class="modal-footer bg-light border-top-0">
                    <button type="button" class="btn btn-white border" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary px-4">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%-- ADD STORAGE MODAL --%>
<div class="modal fade" id="addStorageModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content border-0 shadow">
            <div class="modal-header border-bottom">
                <h5 class="modal-title fw-bold"><i class="bi bi-hdd me-2 text-secondary"></i> Add Storage</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <form action="${pageContext.request.contextPath}/admin/storages" method="POST">
                <div class="modal-body p-4">
                    <div class="mb-2">
                        <label class="form-label fw-bold text-muted small text-uppercase">Storage Capacity</label>
                        <input type="text" class="form-control form-control-lg fs-6" name="capacity"
                               placeholder="e.g. 512GB SSD" required>
                    </div>
                </div>
                <div class="modal-footer bg-light border-top-0">
                    <button type="button" class="btn btn-white border" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary px-4">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%-- ADD BRAND MODAL --%>
<div class="modal fade" id="addBrandModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content border-0 shadow">
            <div class="modal-header border-bottom">
                <h5 class="modal-title fw-bold"><i class="bi bi-award me-2 text-success"></i> Add Brand</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <form action="${pageContext.request.contextPath}/admin/brands" method="POST">
                <div class="modal-body p-4">
                    <div class="mb-2">
                        <label class="form-label fw-bold text-muted small text-uppercase">Brand Name</label>
                        <input type="text" class="form-control form-control-lg fs-6" name="name"
                               placeholder="e.g. Apple, Samsung" required>
                    </div>
                </div>
                <div class="modal-footer bg-light border-top-0">
                    <button type="button" class="btn btn-white border" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary px-4">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>