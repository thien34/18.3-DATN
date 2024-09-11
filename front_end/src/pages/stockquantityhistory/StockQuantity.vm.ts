import { StockQuantityHistoryResponse } from '@/model/StockQuantityHistory'
import { RequestParams } from '@/utils/FetchUtils'
import { useEffect, useState } from 'react'
import useGetAllApi from '@/hooks/use-get-all-api'
import getStockQuantityColumns from './StockQuantityColumns'
import StockQuantityHistoryConfigs from './StockQuantityHistoryConfig'
import { useParams } from 'react-router-dom'

interface Search extends RequestParams {
    page?: number
    productId: string
}
export default function useStockQuantityHistoryViewModel() {
    const [filter, setFilter] = useState<Search>({productId: '1'})
    const { productId: productIdParam } = useParams(); // Get ProductId from URL
    const [productId, setProductId] = useState<string | null>('1');
    useEffect(() => {
        if (productIdParam) {
            setProductId(productIdParam);
            setFilter({ ...filter, productId: productIdParam }); // Update filter with productId
    
        }
        document.title = 'Related Products - Vítore'
    }, [productIdParam])
    // GET COLUMNS
    const columns = getStockQuantityColumns()
    // RETURN DATA
    const { data: listResponse, isLoading } = useGetAllApi<StockQuantityHistoryResponse>(
        StockQuantityHistoryConfigs.resourceUrl,
        StockQuantityHistoryConfigs.resourceKey,
        filter,
    )

    // HANDLE TABLE CHANGE
    const handleTableChange = (pagination: { current: number; pageSize: number }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            page: pagination.current,
        }))
    }
    
    // HANDLE PRODUCT ID CHANGE
  useEffect(() => {
    if (productId) {
      fetch(StockQuantityHistoryConfigs.resourceUrl + '?productId=' + productId);
    }
  }, [productId]);

    return {
        columns,
        filter,
        handleTableChange,
       // handleSearch,
        listResponse,
        isLoading,
    }
}
