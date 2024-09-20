export type StockQuantityHistoryRequest = {
    id?: number
    productId: number
    productAttributeCombinationId?: number
    quantityAdjustment: number
    stockQuantity: number
    message: string
}
export type StockQuantityHistoryResponse = {
    id: number
    attributesXml: string
    quantityAdjustment: number
    stockQuantity: number
    message: string
    createdDate: Date
}
