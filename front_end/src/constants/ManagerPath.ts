class ManagerPath {
    // dashboard
    static readonly DASHBOARD = '/admin/dashboards'
    static readonly PRODUCT_CREATE = '/admin/products/create'

    // product
    static readonly PRODUCT = '/admin/products'

    // product video mapping
    static readonly PRODUCT_VIDEO_MAPPING = '/admin/product-video-mappings'

    // product picture mapping
    static readonly PRODUCT_PICTURE_MAPPING = '/admin/product-picture-mapping'

    // category
    static readonly CATEGORY = '/admin/categories'
    static readonly CATEGORY_ADD = '/admin/categories/add'
    static readonly CATEGORY_UPDATE = '/admin/categories/:id/update'

    // manufacturer
    static readonly MANUFACTURE = '/admin/manufacturers'
    static readonly MANUFACTURE_ADD = '/admin/manufacturers/add'
    static readonly MANUFACTURE_UPDATE = '/admin/manufacturers/:id/update'

    // product tag
    static readonly PRODUCT_TAG = '/admin/product-tags'

    // product attribute
    static readonly PRODUCT_ATTRIBUTE = '/admin/product-attributes'
    static readonly PRODUCT_ATTRIBUTE_ADD = '/admin/product-attributes/add'
    static readonly PRODUCT_ATTRIBUTE_UPDATE = '/admin/product-attributes/:id'

    // specification attribute
    static readonly SPECIFICATION_ATTRIBUTE = '/admin/specification-attributes'
    static readonly SPECIFICATION_ATTRIBUTE_ADD = '/admin/specification-attributes/add'
    static readonly SPECIFICATION_ATTRIBUTE_UPDATE = '/admin/specification-attributes/:id/update'
    static readonly SPECIFICATION_ATTRIBUTE_GROUP_ADD =
        '/admin/specification-attributes/specification-attribute-group-add'
    static readonly SPECIFICATION_ATTRIBUTE_GROUP_UPDATE =
        '/admin/specification-attributes/specification-attribute-group/:id'

    static readonly PRODUCT_UPDATE_SPECIFICATION_ATTRIBUTE_MAPPING = '/admin/products/:productId'
    static readonly PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_UPDATE =
        '/admin/products/product-spec-attribute-mapping/edit/:productId/:id'

    static readonly PRODUCT_LIST_NAME = '/admin/products/list-name'

    static readonly PREDEFINED_PRODUCT_ATTRIBUTE_VALUE = '/admin/predefined-product-attribute-values'

    static readonly PICTURE = '/admin/picture'
    static readonly ORDER = '/admin/orders'

    static readonly PRODUCT_ATTRIBUTE_MAPPING = '/admin/product-attribute-mapping'
    static readonly PRODUCT_ATTRIBUTE_MAPPING_ADD = '/admin/products/product-attribute-mapping-add'
    static readonly PRODUCT_ATTRIBUTE_MAPPING_UPDATE = '/admin/products/product-attribute-mapping-update/:id'

    static readonly STOCK_QUANTITY_HISTORY = '/admin/stock-quantity-history'
    static readonly STOCK_QUANTITY_HISTORY_ADD = '/admin/stock-quantity-history/add'

    static readonly SPECIFICATION_ATTRIBUTE_OPTION = '/admin/specification-attribute-options'

    static readonly PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_ADD = '/admin/product/product-spec-attribute-add'
    static readonly PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING = '/admin/product-specification-attribute-mappings'
    static readonly GET_PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_BY_PRODUCT_ID =
        '/admin/product-specification-attribute-mappings/by-product/:productId'
}

export default ManagerPath
