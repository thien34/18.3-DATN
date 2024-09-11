import { StockQuantityHistoryResponse } from '@/model/StockQuantityHistory'
import formatDate from '@/utils/DateConvertUtils'
import { TableColumnsType } from 'antd'

const getStockQuantityColumns = (): TableColumnsType<StockQuantityHistoryResponse> => [
    {
        title: 'Attributes Combianation',
        dataIndex: 'attributesXml',
        key: 'attributesXml',
        render: (attributesXml: string) => {
            try {
                const parsedData = JSON.parse(attributesXml); // Thử parse chuỗi JSON
                const attributes = parsedData.attributes;
        
                if (!attributes || !Array.isArray(attributes)) {
                    // Kiểm tra nếu không phải mảng hoặc không có attributes
                    return null;
                }
        
                return (
                    <div>
                        {attributes.map((attr: any) => {
                            const key = Object.keys(attr)[0];
                            const value = attr[key];
                            return (
                                <div key={key}>
                                    <strong>{key ? key.charAt(0).toUpperCase()+ key.slice(1).toLowerCase()  + ':' : ''}</strong> {value || ''}
                                </div>
                            );
                        })}
                    </div>
                );
            } catch (error) {
                // Nếu JSON.parse() thất bại, không render gì cả
                return 'Default';
            }
        },
    },
    {
        width: '10%',
        title: 'QuantityAdjustment',
        dataIndex: 'quantityAdjustment',
        key: 'quantityAdjustment',
        render: (quantityAdjustment) => (quantityAdjustment > 0 ? `+${quantityAdjustment}` : `${quantityAdjustment}`),
    },
    {
        width: '10%',
        title: 'StockQuantity',
        dataIndex: 'stockQuantity',
        key: 'stockQuantity',
    },
    {
        width: '30%',
        title: 'Message',
        dataIndex: 'message',
        key: 'message',
    },
    {
        width: '25%',
        title: 'Created Date',
        dataIndex: 'createdDate',
        key: 'createdDate',
        render: (createdDate: Date) => formatDate(createdDate),
    },
]
export default getStockQuantityColumns
