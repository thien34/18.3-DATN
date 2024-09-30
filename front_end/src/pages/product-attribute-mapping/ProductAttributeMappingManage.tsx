import { Collapse, Tabs } from 'antd'
import { ProductAtbCombinationsManage } from '../product-attribute-combinationsV2'
import ProductAttributeMapping from './ProductAttributeMapping'
import ProductAttributeMappingConfigs from './ProductAttributeMappingConfigs'

export default function ProductAttributeMappingManage() {
    return (
        <>
            <Collapse
                collapsible='header'
                defaultActiveKey={['1']}
                className='mb-6'
                items={[
                    {
                        key: '1',
                        label: ProductAttributeMappingConfigs.resourceKey,
                        children: (
                            <Tabs
                                defaultActiveKey='1'
                                type='card'
                                size='middle'
                                items={[
                                    {
                                        key: '1',
                                        label: 'Attributes',
                                        children: <ProductAttributeMapping />,
                                    },
                                    {
                                        key: '2',
                                        label: 'Attribute combinations',
                                        children: <ProductAtbCombinationsManage />,
                                    },
                                ]}
                            />
                        ),
                    },
                ]}
            />
        </>
    )
}
