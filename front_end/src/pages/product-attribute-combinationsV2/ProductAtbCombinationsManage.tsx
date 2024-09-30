/* eslint-disable @typescript-eslint/no-explicit-any */
import AppActions from '@/constants/AppActions'
import useGetByIdApi from '@/hooks/use-get-by-id-api'
import {
    AttributeItemResponse,
    ProductAttributeCombinationRequest,
    ProductAttributeCombinationResponse,
} from '@/model/ProductAttributeCombination'
import { EditOutlined } from '@ant-design/icons'
import { Button, Table } from 'antd'
import useProductAtbCombinationsViewModel from './ProductAtbCombinations.vm'
import ProductAtbCombinationsConfig from './ProductAtbCombinationsConfig'
import ProductAtbCombinationsModal from './ProductAtbCombinationsModal'

export default function ProductAtbCombinationsManage() {
    const columns = [
        {
            title: 'Attributes',
            dataIndex: 'attributeItemResponses',
            key: 'attributeItemResponses',
            width: '35%',
            render: (attributeItemResponses: AttributeItemResponse[]) => {
                return (
                    <div>
                        {attributeItemResponses.map((attr: AttributeItemResponse) => {
                            return (
                                <div key={attr.id}>
                                    {attr.attributeName && (
                                        <>
                                            <strong>{attr.attributeName + ':'}</strong> {attr.itemName}
                                        </>
                                    )}{' '}
                                </div>
                            )
                        })}
                    </div>
                )
            },
        },
        {
            title: 'Stock quantity',
            dataIndex: 'stockQuantity',
            key: 'stockQuantity',
        },
        {
            title: 'Allow out of stock',
            dataIndex: 'allowOutOfStockOrders',
            key: 'allowOutOfStockOrders',
            render: (value: boolean) => (value ? 'Yes' : 'No'),
        },
        {
            title: 'SKU',
            dataIndex: 'sku',
            key: 'sku',
        },
        {
            title: 'Manufacturer part number',
            dataIndex: 'manufacturerPartNumber',
            key: 'manufacturerPartNumber',
        },
        {
            title: 'GTIN',
            dataIndex: 'gtin',
            key: 'gtin',
        },
        {
            title: 'Overridden price',
            dataIndex: 'overriddenPrice',
            key: 'overriddenPrice',
            render: (value: number) => `$${value.toFixed(2)}`,
        },
        {
            title: 'Notify admin for quantity below',
            dataIndex: 'minStockQuantity',
            key: 'minStockQuantity',
        },
        {
            title: 'Picture',
            dataIndex: 'pictureUrl',
            key: 'pictureUrl',
            render: (url: string) => (url ? <img src={url} alt='Product' style={{ width: 50 }} /> : ''),
        },
        {
            title: 'Edit',
            key: 'edit',
            render: (_: any, record: ProductAttributeCombinationRequest) => (
                <Button
                    onClick={() => handleEdit(record)}
                    className='bg-[#475569] text-white border-[#475569]'
                    icon={<EditOutlined />}
                >
                    {AppActions.EDIT}
                </Button>
            ),
        },
        {
            title: 'Delete',
            key: 'delete',
            render: (_: any, record: ProductAttributeCombinationRequest) => (
                <Button onClick={() => handleDelete(record.id)} className='bg-[#475569] text-white border-[#475569]'>
                    {AppActions.DELETE}
                </Button>
            ),
        },
    ]

    const { data } = useGetByIdApi<ProductAttributeCombinationResponse[]>(
        ProductAtbCombinationsConfig.resourceUrlByProductId,
        ProductAtbCombinationsConfig.resourceUrlByProductId,
        16,
    )

    const { open, setOpen, handleDelete, handleEdit, selectedRecord, handleOpenModal } =
        useProductAtbCombinationsViewModel()

    return (
        <div className='bg-[#fff] rounded-lg shadow-md p-6'>
            <Table bordered dataSource={data} columns={columns} />
            <div className='bg-[#d4d2d2c0] h-24 flex  items-center  pl-5 rounded-lg'>
                <Button type='primary' onClick={() => handleOpenModal()}>
                    Add combination
                </Button>
            </div>
            <ProductAtbCombinationsModal selectedRecord={selectedRecord} isModalOpen={open} setIsModalOpen={setOpen} />
        </div>
    )
}
