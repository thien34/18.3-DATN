import useCreateApi from '@/hooks/use-create-api'
import useDeleteByIdApi from '@/hooks/use-delete-by-id-api'
import { useQueryClient } from '@tanstack/react-query'
import { Form, FormProps } from 'antd'
import { useState } from 'react'
import { AttributeItemRequest, ProductAttributeCombinationRequest } from '../../model/ProductAttributeCombination'
import ProductAtbCombinationsConfig from './ProductAtbCombinationsConfig'

export interface ProductAtbMapping {
    id: number
    attName: string
    attributeControlTypeId: string
    isRequired: boolean
    productAttributeValueResponses: ProductAtbMappingValue[]
}

interface ProductAtbMappingValue {
    id: number
    name: string
}

function useProductAtbCombinationsViewModel() {
    const [form] = Form.useForm()

    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const initialValues: { [key: string]: any } = {
        id: null,
        stockQuantity: 1000,
        minStockQuantity: 0,
        allowOutOfStockOrders: false,
        sku: '',
        manufacturerPartNumber: '',
        gtin: '',
        overriddenPrice: 0,
        pictureIds: [],
        attributeItemResponses: [],
    }

    const queryClient = useQueryClient()
    const [open, setOpen] = useState(false)
    const [selectedRecord, setSelectedRecord] = useState<ProductAttributeCombinationRequest | null>(null)
    const [error, setError] = useState('')
    const createApi = useCreateApi<ProductAttributeCombinationRequest>(ProductAtbCombinationsConfig.resourceUrl)
    const { mutate: deleteApi } = useDeleteByIdApi<number>(ProductAtbCombinationsConfig.resourceUrl)

    const handleCreate = async (values: ProductAttributeCombinationRequest) => {
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        const attributeItemRequests: AttributeItemRequest[] = Object.keys(values as any)
            .filter((key) => !isNaN(Number(key)))
            .map((key) => ({
                productId: 16,
                attributeId: Number(key),
                // eslint-disable-next-line @typescript-eslint/no-explicit-any
                itemId: (values as any)[key],
            }))

        const productAttributeCombination = {
            ...values,
            productId: 16,
            attributeItemRequests: attributeItemRequests,
        }

        return new Promise<void>((resolve, reject) => {
            createApi.mutate(productAttributeCombination, {
                onSuccess: async () => {
                    await queryClient.invalidateQueries({
                        queryKey: [ProductAtbCombinationsConfig.resourceUrlByProductId, 'getById', 16],
                    })
                    setError('')
                    resolve()
                },
                onError: (error) => {
                    setError(error.message)
                    reject(error)
                },
            })
        })
    }

    const onFinishFailed: FormProps<ProductAttributeCombinationRequest>['onFinishFailed'] = (errorInfo) => {
        console.log('Failed:', errorInfo)
    }

    const handleDelete = (id: number) => {
        deleteApi(id, {
            onSuccess: () => {
                queryClient.invalidateQueries({
                    queryKey: [ProductAtbCombinationsConfig.resourceUrlByProductId, 'getById', 1],
                })
            },
        })
    }

    const handleEdit = (record: ProductAttributeCombinationRequest) => {
        setSelectedRecord(record)
        const updatedInitialValues = {
            ...initialValues,
            ...record,
        }
        form.setFieldsValue(updatedInitialValues)

        setOpen(true)
    }

    const handleOpenModal = () => {
        setSelectedRecord(null)
        setOpen(true)
    }

    return {
        initialValues,
        handleCreate,
        onFinishFailed,
        open,
        setOpen,
        handleDelete,
        selectedRecord,
        handleEdit,
        error,
        setError,
        handleOpenModal,
    }
}

export default useProductAtbCombinationsViewModel
