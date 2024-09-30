import useGetByIdApi from '@/hooks/use-get-by-id-api'
import { ProductAttributeCombinationRequest } from '@/model/ProductAttributeCombination'
import {
    Button,
    Card,
    Checkbox,
    Form,
    FormProps,
    Image,
    Input,
    InputNumber,
    Modal,
    Radio,
    Select,
    Typography,
} from 'antd'
import { Dispatch, SetStateAction } from 'react'
import useProductAtbCombinationsViewModel, { ProductAtbMapping } from './ProductAtbCombinations.vm'
import ProductAtbCombinationsConfig from './ProductAtbCombinationsConfig'
const { Text } = Typography

type Props = {
    isModalOpen: boolean
    setIsModalOpen: Dispatch<SetStateAction<boolean>>
    selectedRecord: ProductAttributeCombinationRequest | null
}

export default function ProductAtbCombinationsModal({ isModalOpen, selectedRecord, setIsModalOpen }: Readonly<Props>) {
    const [form] = Form.useForm()

    const { handleCreate, onFinishFailed, initialValues, error } = useProductAtbCombinationsViewModel()
    const handleCancel = () => {
        // form.resetFields()
        setIsModalOpen(false)
    }

    const { data } = useGetByIdApi<ProductAtbMapping[]>(
        ProductAtbCombinationsConfig.resourceUrlByProductIdMapping,
        ProductAtbCombinationsConfig.resourceUrlByProductIdMapping,
        16,
    )

    const initialValuesToUse = selectedRecord ? { ...initialValues, ...selectedRecord } : initialValues

    const renderFormItems = () => {
        return (
            data &&
            data.map((item) => {
                const fieldName = item.attName
                const initialValue = initialValuesToUse?.attributeItemResponses[0]?.itemId
                switch (item.attributeControlTypeId) {
                    case 'DROPDOWN':
                        initialValues[fieldName] = initialValuesToUse?.attributeItemResponses[0]?.itemId

                        return (
                            <Form.Item
                                key={item.attName}
                                label={item.attName}
                                name={item.id}
                                initialValue={initialValue}
                                rules={
                                    item.isRequired
                                        ? [{ required: true, message: `Please select a ${item.attName.toLowerCase()}` }]
                                        : []
                                }
                            >
                                {item.productAttributeValueResponses.length > 0 && (
                                    <Select>
                                        {item.productAttributeValueResponses.map((attr) => (
                                            <Select.Option key={attr.id} value={attr.id}>
                                                {attr.name}
                                            </Select.Option>
                                        ))}
                                    </Select>
                                )}
                            </Form.Item>
                        )
                    case 'RADIO_BUTTON':
                        return (
                            <Form.Item
                                key={item.attName}
                                label={item.attName}
                                name={item.id}
                                initialValue={initialValue}
                                rules={
                                    item.isRequired
                                        ? [{ required: true, message: `Please select a ${item.attName.toLowerCase()}` }]
                                        : []
                                }
                            >
                                {item.productAttributeValueResponses.length > 0 && (
                                    <Radio.Group>
                                        {item.productAttributeValueResponses.map((attr) => (
                                            <Radio key={attr.id} value={attr.id}>
                                                {attr.name}
                                            </Radio>
                                        ))}
                                    </Radio.Group>
                                )}
                            </Form.Item>
                        )
                    default:
                        return null
                }
            })
        )
    }

    const onFinish: FormProps<ProductAttributeCombinationRequest>['onFinish'] = async (values) => {
        try {
            await handleCreate(values)
            form.resetFields()
            setIsModalOpen(false)
        } catch (error) {
            console.error(error)
        }
    }
    return (
        <Modal
            maskClosable={false}
            closable={true}
            width={750}
            title='Select new combination and enter details below'
            open={isModalOpen}
            onCancel={handleCancel}
            footer={null}
        >
            {error && (
                <Card title='' className='mb-10' size='small'>
                    <Text type='danger'>{error}</Text>
                </Card>
            )}

            <Form
                form={form}
                name='basic'
                labelCol={{ span: 8 }}
                wrapperCol={{ span: 16 }}
                style={{ maxWidth: 600 }}
                initialValues={initialValuesToUse}
                onFinish={onFinish}
                onFinishFailed={onFinishFailed}
                autoComplete='off'
            >
                {renderFormItems()}
                <Form.Item<ProductAttributeCombinationRequest> label='Stock quantity' name='stockQuantity'>
                    <InputNumber min={1} max={10000} className='w-[100%]' />
                </Form.Item>

                <Form.Item<ProductAttributeCombinationRequest> label='Minimum stock qty' name='minStockQuantity'>
                    <InputNumber min={0} className='w-[100%]' />
                </Form.Item>

                <Form.Item<ProductAttributeCombinationRequest>
                    label='Allow out of stock'
                    valuePropName='checked'
                    name='allowOutOfStockOrders'
                >
                    <Checkbox></Checkbox>
                </Form.Item>

                <Form.Item<ProductAttributeCombinationRequest> label='SKU' name='sku'>
                    <Input />
                </Form.Item>

                <Form.Item<ProductAttributeCombinationRequest>
                    label='Manufacturer part number'
                    name='manufacturerPartNumber'
                >
                    <Input />
                </Form.Item>

                <Form.Item<ProductAttributeCombinationRequest> label='GTIN' name='gtin'>
                    <Input />
                </Form.Item>

                <Form.Item<ProductAttributeCombinationRequest> label='Overridden price' name='overriddenPrice'>
                    <InputNumber min={0} className='w-[100%]' />
                </Form.Item>

                <Form.Item<ProductAttributeCombinationRequest> label='Pictures' name='pictureIds'>
                    <div className='flex items-center -mt-2'>
                        <Checkbox></Checkbox>
                        <Image
                            className='ms-10'
                            width={50}
                            src='https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png'
                        />
                    </div>
                </Form.Item>

                <div style={{ display: 'none' }}>
                    <Form.Item<ProductAttributeCombinationRequest> label='Overridden price' name='id'>
                        <InputNumber className='w-[100%]' />
                    </Form.Item>
                </div>

                <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                    <Button type='primary' htmlType='submit'>
                        Submit
                    </Button>
                </Form.Item>
            </Form>
        </Modal>
    )
}
