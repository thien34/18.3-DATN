export interface ProductAttributeCombinationRequest {
    id: number
    stockQuantity?: number
    minStockQuantity?: number
    overriddenPrice?: number
    allowOutOfStockOrders?: false
    sku: string
    manufacturerPartNumber: string
    gtin: string
    pictureIds: []
    attributeItemRequests: AttributeItemRequest[]
}

export interface AttributeItemRequest {
    productId: number
    attributeId: number
    itemId: number
}

export interface ProductAttributeCombinationResponse {
    attributeItemRequests: never[]
    id: number
    stockQuantity?: number
    minStockQuantity?: number
    overriddenPrice?: number
    allowOutOfStockOrders?: false
    sku: string
    manufacturerPartNumber: string
    gtin: string
    pictureUrl: string
    attributesXml: string
    pictureIds: []
    attributeItemResponses: AttributeItemResponse[]
}

export interface AttributeItemResponse {
    id: number | null
    productId: number | null
    attributeId: number
    attributeName: string
    itemId: number
    itemName: string
}
