import { SpecificationAttributeRequest, SpecificationAttributeResponse } from '@/model/SpecificationAttribute.ts'

export type SpecificationAttributeGroupRequest = {
    id: number
    name: string
    displayOrder: number
    specificationAttributes?: SpecificationAttributeRequest
}
export type SpecificationAttributeGroupResponse = {
    id: number
    name: string
    displayOrder: number
    specificationAttributes: SpecificationAttributeResponse[]
}
export type SpecificationAttributeGroupNameResponse = {
    id: number
    name: string
}
